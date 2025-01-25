package com.am.jarvis.connector.mqtt

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Verification for [MqttConfiguration].
 *
 * @author Alex Mikhalochkin
 */
class MqttConfigurationTest {

    private val mqttConfiguration = MqttConfiguration()

    @Test
    fun testMqttConnectOptions() {
        val username = "username"
        val password = "password"

        val options = mqttConfiguration.mqttConnectOptions(username, password)

        assertEquals(username, options.userName)
        assertArrayEquals(password.toCharArray(), options.password)
        assertTrue(options.isAutomaticReconnect)
        assertTrue(options.isCleanSession)
    }
}
