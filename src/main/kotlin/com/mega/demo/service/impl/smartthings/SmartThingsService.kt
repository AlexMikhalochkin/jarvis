package com.mega.demo.service.impl.smartthings

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceStatus
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.PlcService
import com.mega.demo.service.api.SmartHomeService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService] for SmartThings.
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartThingsService(val deviceRepository: DeviceRepository, val plcService: PlcService) : SmartHomeService {

    override fun getStatuses(deviceIds: List<String>): List<DeviceStatus> {
        val portStatuses = plcService.getPortStatuses()
        return deviceRepository.findPorts(deviceIds)
            .map { (deviceId, port) -> DeviceStatus(deviceId, portStatuses.getValue(port)) }
            .toList()
    }

    override fun changeStatus(listOf: List<String>, on: Boolean): List<DeviceStatus> {
        return listOf(DeviceStatus("kitchen-light-0", on))
    }

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }
}
