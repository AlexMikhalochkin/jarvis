package com.am.jarvis.connector.yandex.converter

import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceName
import com.am.jarvis.core.model.Room
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.core.convert.converter.Converter
import java.util.UUID

/**
 * Base test for converters.
 *
 * @author Alex Mikhalochkin
 */
abstract class BaseConverterTest<S : Any, E> {

    val externalDeviceId = UUID.randomUUID().toString()

    @Test
    fun testConvert() {
        assertEquals(createExpected(), getConverter().convert(createSource()))
    }

    abstract fun getConverter(): Converter<S, E>

    abstract fun createSource(): S

    abstract fun createExpected(): E

    fun createDevice(): Device {
        return Device(
            externalDeviceId,
            Room("Kitchen", "спальня"),
            DeviceName("friendly name", "свет на кухне"),
            "цветная лампа",
            listOf("Kitchen Lights", "House Bulbs"),
            mapOf("port" to 7)
        )
    }
}
