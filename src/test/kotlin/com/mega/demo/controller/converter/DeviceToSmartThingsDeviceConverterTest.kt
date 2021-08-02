package com.mega.demo.controller.converter

import com.mega.demo.controller.model.smartthings.SmartThingsDevice
import com.mega.demo.model.Device
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [DeviceToSmartThingsDeviceConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceToSmartThingsDeviceConverterTest {

    private val externalDeviceId = UUID.randomUUID().toString()
    private val deviceUniqueId = UUID.randomUUID().toString()

    @Test
    fun testConvert() {
        assertEquals(createExpected(), DeviceToSmartThingsDeviceConverter().convert(createSource()))
    }

    private fun createExpected(): SmartThingsDevice {
        return SmartThingsDevice(
            externalDeviceId,
            emptyMap(),
            "friendly name",
            emptyMap(),
            emptyMap(),
            "handler type",
            deviceUniqueId
        )
    }

    private fun createSource(): Device {
        return Device(
            externalDeviceId,
            emptyMap(),
            "friendly name",
            emptyMap(),
            emptyMap(),
            "handler type",
            deviceUniqueId
        )
    }
}
