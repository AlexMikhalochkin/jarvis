package com.am.jarvis.connector.yandex.notifier.converter

import com.am.jarvis.core.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [CapabilitiesConverter].
 *
 * @author Alex Mikhalochkin
 */
class CapabilitiesConverterTest {

    private val converter = CapabilitiesConverter()

    @Test
    fun convert() {
        val result = converter.convert(DeviceState("id", false))

        assertEquals(1, result.size)
        val capability = result[0]
        assertEquals("devices.capabilities.on_off", capability.type)
        assertEquals("on", capability.state.instance)
        assertEquals(false, capability.state.value)
    }

    @Test
    fun convertSensor() {
        val result = converter.convert(DeviceState("id", false, mapOf("battery" to 3.0)))

        assertEquals(0, result.size)
    }
}
