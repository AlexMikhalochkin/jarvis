package com.mega.demo

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils

private const val URL = "http://test.test/test"

/**
 * Verification for [DemoApplication].
 *
 * @author Alex Mikhalochkin
 */
class DemoApplicationTest {

    private val demoApplication = DemoApplication()

    @Test
    fun testSmartThingsWebClient() {
        ReflectionTestUtils.setField(demoApplication, "smartThingsUrl", URL)
        assertNotNull(demoApplication.smartThingsWebClient())
    }

    @Test
    fun testYandexWebClient() {
        ReflectionTestUtils.setField(demoApplication, "yandexUrl", URL)
        assertNotNull(demoApplication.yandexWebClient())
    }

    @Disabled("TCP connection fails CI")
    @Test
    fun testMqttClient() {
        val url = "tcp://localhost"
        ReflectionTestUtils.setField(demoApplication, "mqttServerUrl", url)
        val mqttClient = demoApplication.mqttClient(mockk())
        assertNotNull(mqttClient)
        assertEquals("jarvis", mqttClient.clientId)
        assertEquals(url, mqttClient.serverURI)
    }
}
