package com.am.jarvis.connector.megad.mqtt

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Verification for [MegaDMqttConnectOptions].
 *
 * @author Alex Mikhalochkin
 */
class MegaDMqttConnectOptionsTest {

    @Test
    fun testMegaDMqttConnectOptions() {
        val username = "username"
        val password = "password"

        val options = MegaDMqttConnectOptions(username, password)

        assertEquals(username, options.userName)
        assertArrayEquals(password.toCharArray(), options.password)
        assertTrue(options.isAutomaticReconnect)
        assertTrue(options.isCleanSession)
    }
}
