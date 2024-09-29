package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.State
import com.am.jarvis.core.model.DeviceState
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
        assertEquals(createExpected("off"), getConverter().convert(DeviceState("id", false, mapOf("port" to 1))))
    }

    override fun getConverter() = DeviceStateToSmartThingsDeviceStateConverter()

    override fun createSource() = DeviceState("id", true, mapOf("port" to 1))

    override fun createExpected(): com.am.jarvis.controller.generated.model.DeviceState {
        return createExpected("on")
    }

    private fun createExpected(value: String): com.am.jarvis.controller.generated.model.DeviceState {
        val state = State(capability = "st.switch", attribute = "switch", value = value)
        return com.am.jarvis.controller.generated.model.DeviceState("id", emptyMap(), listOf(state))
    }
}
