package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.Command
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.model.DeviceState
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
        assertEquals(DeviceState(externalDeviceId, 1, false), getConverter().convert(createSource("off")))
    }

    override fun getConverter() = SmartThingsDeviceToDeviceStateConverter()

    override fun createSource(): SmartThingsDevice {
        return createSource("on")
    }

    override fun createExpected() = DeviceState(externalDeviceId, 1, true)

    private fun createSource(command: String): SmartThingsDevice {
        return SmartThingsDevice(
            externalDeviceId,
            mapOf("port" to 1),
            commands = listOf(Command(command = command))
        )
    }
}
