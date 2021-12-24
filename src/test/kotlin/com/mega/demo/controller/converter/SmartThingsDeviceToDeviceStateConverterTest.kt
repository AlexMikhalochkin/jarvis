package com.mega.demo.controller.converter

import com.mega.demo.controller.generated.model.Command
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.model.DeviceState
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
