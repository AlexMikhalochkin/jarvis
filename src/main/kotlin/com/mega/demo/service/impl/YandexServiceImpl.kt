package com.mega.demo.service.impl

import com.mega.demo.model.Device
import com.mega.demo.model.yandex.ActionResult
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.DeviceStateHolder
import com.mega.demo.model.yandex.State
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

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceStateHolder> {
        val capability = Capability("devices.capabilities.on_off", State("on", true))
        val device = DeviceStateHolder(
            "id",
            capabilities = listOf(capability),
        )
        return listOf(device)
    }

    override fun changeState(devicesWithStates: List<DeviceStateHolder>): List<DeviceStateHolder> {
        val capability = Capability("devices.capabilities.on_off", State("on", true, ActionResult()))
        val device = DeviceStateHolder(
            "id",
            capabilities = listOf(capability),
        )
        return listOf(device)
    }

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }
}
