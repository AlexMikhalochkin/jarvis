package com.mega.demo.service.impl

import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.SmartThingsDevice
import com.mega.demo.controller.model.smartthings.State
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.PlcService
import com.mega.demo.service.api.SmartThingsService
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartThingsService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartThingsServiceImpl(
    val deviceRepository: DeviceRepository,
    val plcService: PlcService,
    val conversionService: ConversionService
) : SmartThingsService {

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        val portStatuses = plcService.getPortStatuses()
        return deviceRepository.findPorts(deviceIds)
            .map { (deviceId, port) -> deviceState(deviceId, portStatuses.getValue(port)) }
            .toList()
    }

    override fun changeState(devicesWithStates: List<SmartThingsDevice>): List<DeviceState> {
        val idsToPorts = deviceRepository.findPorts(devicesWithStates.map { it.externalDeviceId })
        return devicesWithStates
            .associate { it.externalDeviceId to it.commands!![0].command }
            .onEach { (id, command) -> execute(idsToPorts[id]!!, command) }
            .map { (id, command) -> deviceState(id, command) }
            .toList()
    }

    override fun getAllDevices(): List<SmartThingsDevice> {
        return deviceRepository.findAll()
            .mapNotNull { conversionService.convert(it, SmartThingsDevice::class.java) }
    }

    private fun deviceState(deviceId: String, portStatus: Boolean): DeviceState {
        return deviceState(deviceId, if (portStatus) "on" else "off")
    }

    private fun deviceState(deviceId: String, portStatus: String): DeviceState {
        val state = State("st.switch", "switch", portStatus)
        return DeviceState(deviceId, listOf(state))
    }

    private fun execute(port: Int, command: String) {
        if ("on" == command) plcService.turnOn(port) else plcService.turnOff(port)
    }
}
