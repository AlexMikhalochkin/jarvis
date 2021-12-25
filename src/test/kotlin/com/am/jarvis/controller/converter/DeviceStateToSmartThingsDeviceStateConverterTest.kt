package com.am.jarvis.controller.converter

import com.am.jarvis.controller.generated.model.State
import com.am.jarvis.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [DeviceStateToSmartThingsDeviceStateConverter].
 *
 * @author Alex Mikhalochkin
 */
internal class DeviceStateToSmartThingsDeviceStateConverterTest :
    BaseConverterTest<DeviceState, com.am.jarvis.controller.generated.model.DeviceState>() {

    @Test
    fun testConvertOff() {
        assertEquals(createExpected("off"), getConverter().convert(DeviceState("id", 1, false)))
    }

    override fun getConverter() = DeviceStateToSmartThingsDeviceStateConverter()

    override fun createSource() = DeviceState("id", 1, true)

    override fun createExpected(): com.am.jarvis.controller.generated.model.DeviceState {
        return createExpected("on")
    }

    private fun createExpected(value: String): com.am.jarvis.controller.generated.model.DeviceState {
        val state = State(capability = "st.switch", attribute = "switch", value = value)
        return com.am.jarvis.controller.generated.model.DeviceState("id", emptyMap(), listOf(state))
    }
}
