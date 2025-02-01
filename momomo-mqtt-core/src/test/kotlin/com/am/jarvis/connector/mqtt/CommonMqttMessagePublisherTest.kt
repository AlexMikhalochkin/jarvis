package com.am.jarvis.connector.mqtt

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Tests for [CommonMqttMessagePublisher].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class CommonMqttMessagePublisherTest {

    @MockK
    private lateinit var mqttClientPublisher: IMqttClient

    @InjectMockKs
    private lateinit var commonMqttMessagePublisher: CommonMqttMessagePublisher

    @Test
    fun publishMessageSuccessfully() {
        val topic = "test/topic"
        val message = "test message"

        every { mqttClientPublisher.publish(topic, any(), any(), any()) } returns Unit

        commonMqttMessagePublisher.publish(topic, message)

        verify { mqttClientPublisher.publish(topic, message.toByteArray(), 0, true) }
    }
}
