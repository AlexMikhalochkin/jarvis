package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.DeviceContext
import com.mega.demo.controller.generated.model.ManufacturerInfo
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.generateUuid
import com.mega.demo.model.Device
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [DeviceToSmartThingsDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToSmartThingsDeviceConverterTest {

    private val externalDeviceId = generateUuid()
    private val deviceUniqueId = generateUuid()

    @Test
    fun testConvert() {
        assertEquals(createExpected(), DeviceToSmartThingsDeviceConverter().convert(createSource()))
    }

    private fun createExpected(): SmartThingsDevice {
        return SmartThingsDevice(
            externalDeviceId,
            emptyMap(),
            "friendly name",
            ManufacturerInfo(
                "LIFX",
                "A19 Color Bulb",
                "v1 US bulb",
                "23.123.231"
            ),
            DeviceContext(
                "Kitchen",
                listOf("Kitchen Lights", "House Bulbs"),
                listOf("light", "switch")
            ),
            "handler type",
            deviceUniqueId
        )
    }

    private fun createSource(): Device {
        return Device(
            externalDeviceId,
            emptyMap(),
            "friendly name",
            mapOf(
                "manufacturerName" to "LIFX",
                "modelName" to "A19 Color Bulb",
                "hwVersion" to "v1 US bulb",
                "swVersion" to "23.123.231"
            ),
            mapOf(
                "roomName" to "Kitchen",
                "groups" to listOf("Kitchen Lights", "House Bulbs"),
                "categories" to listOf("light", "switch")
            ),
            "handler type",
            deviceUniqueId
        )
    }
}
