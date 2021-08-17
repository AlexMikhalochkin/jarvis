package com.mega.demo.repository.impl

import com.mega.demo.model.Provider
import com.mega.demo.model.Device
import com.mega.demo.model.TechnicalInfo
import com.mega.demo.model.yandex.Capability
import com.mega.demo.repository.api.DeviceRepository
import org.springframework.stereotype.Repository

/**
 * Implementation of [DeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
@Repository
class DeviceRepositoryImpl : DeviceRepository {

    private val port = 7

    override fun findAll(): List<Device> {
        val device = Device(
            "kitchen-light-0",
            mapOf(Provider.YANDEX to "спальня", Provider.SMART_THINGS to "Kitchen"),
            mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "c2c-rgbw-color-bulb"),
            mapOf(Provider.YANDEX to "свет на кухне", Provider.SMART_THINGS to "Kitchen Bulb"),
            TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
            mapOf("foo" to "bar", "updatedcookie" to "old or new value"),
            "цветная лампа",
            listOf(Capability("devices.capabilities.on_off")),
            listOf("light", "switch"),
            listOf("Kitchen Lights", "House Bulbs")
        )
        return listOf(device)
    }

    override fun findPorts(deviceIds: List<String>): Map<String, Int> {
        return mapOf(deviceIds[0] to port)
    }
}
