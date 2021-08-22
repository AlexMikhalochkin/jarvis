package com.mega.demo.service.impl

import com.mega.demo.integration.PortStatusMessage
import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.PlcService
import com.mega.demo.service.api.Sender
import com.mega.demo.service.api.SmartHomeService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartHomeServiceImpl(
    val deviceRepository: DeviceRepository,
    val plcService: PlcService,
    val messageSender: Sender
) : SmartHomeService {

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        val portStatuses = plcService.getPortStatuses()
        return deviceRepository.findPorts(deviceIds)
            .map { (deviceId, port) -> DeviceState(deviceId, portStatuses.getValue(port)) }
            .toList()
    }

    override fun changeState(states: List<DeviceState>): List<DeviceState> {
        val idsToPorts = deviceRepository.findPorts(states.map { it.deviceId })
        return states
            .associate { it.deviceId to it.isOn }
            .onEach { (id, command) -> execute(idsToPorts[id]!!, command!!) }
            .map { (id, command) -> DeviceState(id, command!!) }
            .toList()
    }

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }

    private fun execute(port: Int, command: Boolean) {
        messageSender.send(PortStatusMessage(port, command))
    }
}
