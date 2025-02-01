package com.am.jarvis.connector.yandex.notifier.converter

import com.am.jarvis.controller.generated.model.FullCapability
import com.am.jarvis.controller.generated.model.Property
import com.am.jarvis.core.model.DeviceState
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.core.convert.converter.Converter

/**
 * Verification for [StateConverter].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class StateConverterTest {

    private val deviceState = DeviceState("id", false)

    @MockK
    lateinit var propertiesConverter: Converter<DeviceState, List<Property>>

    @MockK
    lateinit var capabilitiesConverter: Converter<DeviceState, List<FullCapability>>

    @InjectMockKs
    private lateinit var converter: StateConverter

    @Test
    fun testConvert() {
        val capabilities = listOf<FullCapability>()
        val properties = listOf<Property>()
        every { capabilitiesConverter.convert(any()) } returns capabilities
        every { propertiesConverter.convert(any()) } returns properties

        val result = converter.convert(deviceState)

        assertEquals(deviceState.deviceId, result.id)
        assertSame(capabilities, result.capabilities)
        assertSame(properties, result.properties)
    }

    @Test
    fun testConvertCapabilitiesIsNull() {
        val properties = listOf<Property>()
        every { capabilitiesConverter.convert(any()) } returns null
        every { propertiesConverter.convert(any()) } returns properties

        val result = converter.convert(deviceState)

        assertEquals(deviceState.deviceId, result.id)
        assertEquals(listOf<FullCapability>(), result.capabilities)
        assertSame(properties, result.properties)
    }
}
