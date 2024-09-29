package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.Command
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.core.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [SmartThingsDeviceToDeviceStateConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class SmartThingsDeviceToDeviceStateConverterTest : BaseConverterTest<SmartThingsDevice, DeviceState>() {

    @Test
    fun testConvertOff() {
        val deviceState = DeviceState(externalDeviceId, false, mapOf("port" to 1))

        assertEquals(deviceState, getConverter().convert(createSource("off")))
    }

    override fun getConverter() = SmartThingsDeviceToDeviceStateConverter()

    override fun createSource(): SmartThingsDevice {
        return createSource("on")
    }

    override fun createExpected() = DeviceState(externalDeviceId, true, mapOf("port" to 1))

    private fun createSource(command: String): SmartThingsDevice {
        return SmartThingsDevice(
            externalDeviceId,
            mapOf("port" to 1),
            commands = listOf(Command(command = command))
        )
    }
}
