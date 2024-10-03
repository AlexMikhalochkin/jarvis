package com.am.jarvis.connector.mqtt

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * Verification for [CommonMqttClient].
 *
 * @author Alex Mikhalochkin
 */
class CommonMqttClientTest {

    @Test
    fun testClose() {
        val mqttClient = mockk<CommonMqttClient>(relaxUnitFun = true)
        every { mqttClient.isConnected } returns true
        every { mqttClient.close() } answers { callOriginal() }

        mqttClient.close()

        verify { mqttClient.disconnect() }
    }

    @Test
    fun testCloseNotConnected() {
        val mqttClient = mockk<CommonMqttClient>(relaxUnitFun = true)
        every { mqttClient.isConnected } returns false
        every { mqttClient.close() } answers { callOriginal() }

        mqttClient.close()

        verify { mqttClient.close(true) }
    }
}
