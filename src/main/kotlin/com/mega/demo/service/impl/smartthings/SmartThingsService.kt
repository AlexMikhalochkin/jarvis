package com.mega.demo.service.impl.smartthings

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceStatus
import com.mega.demo.repository.DeviceRepository
import com.mega.demo.service.api.PlcService
import com.mega.demo.service.api.SmartHomeService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService] for Samsung SmartThings.
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartThingsService(val deviceRepository: DeviceRepository, val plcService: PlcService) : SmartHomeService {

    override fun getStatuses(listOf: List<String>): List<DeviceStatus> {
        return listOf(DeviceStatus("kitchen-light-0", true))
    }

    override fun changeStatus(listOf: List<String>, on: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }
}
