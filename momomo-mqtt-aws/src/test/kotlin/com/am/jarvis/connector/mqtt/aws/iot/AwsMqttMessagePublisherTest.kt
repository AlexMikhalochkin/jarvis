package com.am.jarvis.connector.mqtt.aws.iot

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import software.amazon.awssdk.crt.mqtt.MqttClientConnection
import software.amazon.awssdk.crt.mqtt.MqttMessage
import software.amazon.awssdk.crt.mqtt.QualityOfService
import java.util.concurrent.CompletableFuture

/**
 * Tests for [AwsMqttMessagePublisher].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class AwsMqttMessagePublisherTest {

    @InjectMockKs
    private lateinit var awsMqttMessagePublisher: AwsMqttMessagePublisher

    @MockK
    private lateinit var mqttClientConnection: MqttClientConnection

    @Test
    fun publish() {
        val topic = "topic"
        val message = "message"
        val future = mockk<CompletableFuture<Int>>()
        every { mqttClientConnection.publish(any()) } returns future
        every { future.get() } returns 0
        val messageSlot = slot<MqttMessage>()

        awsMqttMessagePublisher.publish(topic, message)

        verify { mqttClientConnection.publish(capture(messageSlot)) }
        verify { future.get() }
        assertEquals(topic, messageSlot.captured.topic)
        assertArrayEquals(message.toByteArray(), messageSlot.captured.payload)
        assertEquals(QualityOfService.AT_MOST_ONCE, messageSlot.captured.qos)
        assertTrue(messageSlot.captured.retain)
    }
}
