package com.am.jarvis.connector.yandex.notifier.converter

import com.am.jarvis.core.model.DeviceState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Verification for [PropertiesConverter].
 *
 * @author Alex Mikhalochkin
 */
class PropertiesConverterTest {

    private val converter = PropertiesConverter()

    @Test
    fun convert() {
        val customData = mapOf(
            "temperature" to 2.0
        )
        val source = DeviceState("id", false, customData)

        val convert = converter.convert(source)

        assertEquals(1, convert.size)
        val property = convert[0]
        assertEquals("temperature", property.state.instance)
        assertEquals(2.0, property.state.value)
        assertEquals("devices.capabilities.float", property.type)
    }

    @Test
    fun convertAll() {
        val customData = mapOf(
            "humidity" to 1.0,
            "temperature" to 2.0,
            "battery" to 3.0,
            "button" to "click",
            "voltage" to 4.0
        )
        val source = DeviceState("id", false, customData)

        val convert = converter.convert(source)

        assertEquals(5, convert.size)
    }
}
