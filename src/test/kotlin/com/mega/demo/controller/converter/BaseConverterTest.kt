package com.mega.demo.controller.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.core.convert.converter.Converter

/**
 * Base test for converters.
 *
 * @author Alex Mikhalochkin
 */
abstract class BaseConverterTest<S : Any, E> {

    @Test
    fun testConvert() {
        assertEquals(createExpected(), getConverter().convert(createSource()))
    }

    abstract fun getConverter(): Converter<S, E>

    abstract fun createSource(): S

    abstract fun createExpected(): E
}
