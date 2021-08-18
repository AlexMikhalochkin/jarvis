package com.mega.demo.service.impl

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.YandexService
import org.springframework.stereotype.Service

/**
 * Implementation of [YandexService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class YandexServiceImpl(val deviceRepository: DeviceRepository) : YandexService {

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return listOf(DeviceState("id", isOn = true))
    }

    override fun changeState(devicesWithStates: List<DeviceState>): List<DeviceState> {
        return listOf(DeviceState("id"))
    }

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }
}
