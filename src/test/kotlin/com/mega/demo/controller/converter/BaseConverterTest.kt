package com.mega.demo.controller.converter

import com.mega.demo.generateUuid
import com.mega.demo.model.Device
import com.mega.demo.model.Provider
import com.mega.demo.model.TechnicalInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.core.convert.converter.Converter

/**
 * Base test for converters.
 *
 * @author Alex Mikhalochkin
 */
abstract class BaseConverterTest<S : Any, E> {

    val externalDeviceId = generateUuid()

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
            7,
            mapOf(Provider.YANDEX to "спальня", Provider.SMART_THINGS to "Kitchen"),
            mapOf(Provider.YANDEX to "devices.types.light", Provider.SMART_THINGS to "handler type"),
            mapOf(Provider.YANDEX to "свет на кухне", Provider.SMART_THINGS to "friendly name"),
            TechnicalInfo("Provider2", "hue g11", "1.0", "1.0"),
            "цветная лампа",
            listOf("devices.capabilities.on_off"),
            listOf("light", "switch"),
            listOf("Kitchen Lights", "House Bulbs")
        )
    }
}
