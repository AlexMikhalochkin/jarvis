package com.mega.demo.service.impl

import com.mega.demo.model.yandex.ActionResult
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.Device
import com.mega.demo.model.yandex.DeviceInfo
import com.mega.demo.model.yandex.State
import com.mega.demo.service.api.YandexService
import org.springframework.stereotype.Service

/**
 * Implementation of [YandexService] for Yandex.
 *
 * @author Alex Mikhalochkin
 */
@Service
class YandexServiceImpl : YandexService {

    override fun getDeviceStates(deviceIds: List<String>): List<Device> {
        val capability = Capability("devices.capabilities.on_off", State("on", true))
        val device = Device(
            "id",
            capabilities = listOf(capability),
        )
        return listOf(device)
    }

    override fun changeState(devicesWithStates: List<Device>): List<Device> {
        val capability = Capability("devices.capabilities.on_off", State("on", true, ActionResult()))
        val device = Device(
            "id",
            capabilities = listOf(capability),
        )
        return listOf(device)
    }

    override fun getAllDevices(): List<Device> {
        val device = Device(
            "id",
            "свет на кухне",
            "цветная лампа",
            "спальня",
            "devices.types.light",
            mapOf("foo" to "bar"),
            listOf(Capability("devices.capabilities.on_off")),
            DeviceInfo("Provider2", "hue g11", "1.0", "1.0")
        )
        return listOf(device)
    }
}
