package com.am.jarvis.integration.impl

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [MqttMessageSender].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class MqttMessageSenderTest {

    @MockK(relaxUnitFun = true)
    lateinit var client: IMqttClient

    @InjectMockKs
    lateinit var messageSender: MqttMessageSender

    @Test
    fun testSend() {
        val message = "test"
        messageSender.send(message)
        verify { client.publish("megad/14/cmd", message.toByteArray(), 0, true) }
    }
}
