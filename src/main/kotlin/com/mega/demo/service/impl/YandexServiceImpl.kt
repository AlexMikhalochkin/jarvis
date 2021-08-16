package com.mega.demo.service.impl

import com.mega.demo.model.Provider
import com.mega.demo.model.SmartDevice
import com.mega.demo.model.TechnicalInfo
import com.mega.demo.model.yandex.ActionResult
import com.mega.demo.model.yandex.Capability
import com.mega.demo.model.yandex.DeviceStateHolder
import com.mega.demo.model.yandex.State
import com.mega.demo.service.api.YandexService
import org.springframework.stereotype.Service

/**
 * Implementation of [YandexService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class YandexServiceImpl : YandexService {

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

    override fun getAllDevices(): List<SmartDevice> {
        val smartDevice = SmartDevice(
            "id",
            mapOf(Provider.YANDEX to "спальня"),
            mapOf(Provider.YANDEX to "devices.types.light"),
            mapOf(Provider.YANDEX to "свет на кухне"),
            TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
            mapOf("foo" to "bar"),
            "цветная лампа",
            listOf(Capability("devices.capabilities.on_off")),
        )
        return listOf(smartDevice)
    }
}
