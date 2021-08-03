package com.mega.demo.repository.impl

import com.mega.demo.model.Device
import com.mega.demo.repository.api.DeviceRepository
import org.springframework.stereotype.Repository

/**
 * Implementation of [DeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
@Repository
class DeviceRepositoryImpl : DeviceRepository {

    override fun findAll(): List<Device> {
        val deviceContext = mapOf(
            "roomName" to "Kitchen",
            "groups" to listOf("Kitchen Lights", "House Bulbs"),
            "categories" to listOf("light", "switch")
        )
        return listOf(
            Device(
                "kitchen-light-0",
                mapOf("updatedcookie" to "old or new value"),
                "Kitchen Bulb",
                mapOf("manufacturerName" to "LIFX", "modelName" to "A19 Color Bulb"),
                deviceContext,
                "c2c-rgbw-color-bulb",
                "unique identifier of device"
            )
        )
    }

    override fun findPorts(deviceIds: List<String>): Map<String, Int> {
        return mapOf(deviceIds[0] to 1)
    }
}
