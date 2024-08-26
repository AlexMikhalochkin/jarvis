package com.am.jarvis

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

private const val URL = "http://test.test/test"

/**
 * Verification for [JarvisApplication].
 *
 * @author Alex Mikhalochkin
 */
class JarvisApplicationTest {

    private val jarvisApplication = JarvisApplication()

    @Test
    fun testSmartThingsWebClient() {
        ReflectionTestUtils.setField(jarvisApplication, "smartThingsUrl", URL)
        assertNotNull(jarvisApplication.smartThingsWebClient())
    }

    @Test
    fun testYandexWebClient() {
        ReflectionTestUtils.setField(jarvisApplication, "yandexUrl", URL)
        assertNotNull(jarvisApplication.yandexWebClient())
    }
}
