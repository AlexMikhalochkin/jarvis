package com.am.jarvis.connector.megad.mqtt

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource

/**
 * Verification for [MegaDPortStateDeserializer].
 *
 * @author Alex Mikhalochkin
 */
class MegaDPortStateDeserializerTest {

    private val deserializer = MegaDPortStateDeserializer()

    @Test
    fun deserializeReturnsTrueWhenTextIsON() {
        val jsonParser = mockk<JsonParser>()
        val context = mockk<DeserializationContext>()
        every { jsonParser.text }.returns("ON")

        assertTrue(deserializer.deserialize(jsonParser, context))
        verify { context wasNot Called }
    }

    @Test
    fun deserializeParserIsNull() {
        val context = mockk<DeserializationContext>()

        assertFalse(deserializer.deserialize(null, context))
        verify { context wasNot Called }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = ["OFF", "INVALID", " ", "on"])
    fun deserializeReturnsFalseWhenTextIsOFF(value: String?) {
        val jsonParser = mockk<JsonParser>()
        val context: DeserializationContext = mockk()
        every { jsonParser.text }.returns(value)

        assertFalse(deserializer.deserialize(jsonParser, context))
        verify { context wasNot Called }
    }
}
