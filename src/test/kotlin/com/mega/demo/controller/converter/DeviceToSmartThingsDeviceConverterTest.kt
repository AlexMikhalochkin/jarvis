package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.DeviceContext
import com.mega.demo.controller.generated.model.ManufacturerInfo
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.generateUuid
import com.mega.demo.model.Device
import com.mega.demo.model.Provider
import com.mega.demo.model.TechnicalInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [DeviceToSmartThingsDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToSmartThingsDeviceConverterTest {

    private val externalDeviceId = generateUuid()

    @Test
    fun testConvert() {
        assertEquals(createExpected(), DeviceToSmartThingsDeviceConverter().convert(createSource()))
    }

    private fun createExpected(): SmartThingsDevice {
        return SmartThingsDevice(
            externalDeviceId,
            mapOf("port" to 7),
            "friendly name",
            ManufacturerInfo(
                "Provider2",
                "hue g11",
                "1.0",
                "1.0"
            ),
            DeviceContext(
                "Kitchen",
                listOf("Kitchen Lights", "House Bulbs"),
                listOf("light", "switch")
            ),
            "handler type",
            externalDeviceId
        )
    }

    private fun createSource(): Device {
        return Device(
            externalDeviceId,
            7,
            mapOf(Provider.YANDEX to "спальня", Provider.SMART_THINGS to "Kitchen"),
            mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "handler type"),
            mapOf(Provider.YANDEX to "свет на кухне", Provider.SMART_THINGS to "friendly name"),
            TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
            "цветная лампа",
            listOf("devices.capabilities.on_off"),
            listOf("light", "switch"),
            listOf("Kitchen Lights", "House Bulbs")
        )
    }
}
