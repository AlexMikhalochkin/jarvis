package com.am.jarvis

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
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

    @Disabled("TCP connection fails CI")
    @Test
    fun testMqttClient() {
        val url = "tcp://localhost"
        ReflectionTestUtils.setField(jarvisApplication, "mqttServerUrl", url)
        val mqttClient = jarvisApplication.mqttClient(mockk())
        assertNotNull(mqttClient)
        assertEquals("jarvis", mqttClient.clientId)
        assertEquals(url, mqttClient.serverURI)
    }
}
