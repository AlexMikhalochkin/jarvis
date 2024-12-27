package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.SmartThingsCallbackState
import com.am.jarvis.core.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Tests for [SmartThingsConverter].
 */
class SmartThingsConverterTest {

    private val converter = SmartThingsConverter()

    @Test
    fun convertsDeviceStateToSmartThingsDeviceState() {
        val deviceId = "deviceId"
        val deviceState = DeviceState(deviceId, true)
        val expectedCallbackState = SmartThingsCallbackState(
            timestamp = System.currentTimeMillis().div(1000),
            value = "on",
            capability = "st.switch",
            attribute = "switch"
        )

        val result = converter.convert(deviceState)

        assertEquals(deviceId, result.externalDeviceId)
        assertEquals(expectedCallbackState.value, result.states!![0].value)
        assertEquals(expectedCallbackState.capability, result.states[0].capability)
        assertEquals(expectedCallbackState.attribute, result.states[0].attribute)
    }
}
