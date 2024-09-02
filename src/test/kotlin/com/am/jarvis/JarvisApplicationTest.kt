package com.am.jarvis

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

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
        assertNotNull(jarvisApplication.smartThingsWebClient(URL))
    }

    @Test
    fun testYandexWebClient() {
        assertNotNull(jarvisApplication.yandexWebClient(URL))
    }
}
