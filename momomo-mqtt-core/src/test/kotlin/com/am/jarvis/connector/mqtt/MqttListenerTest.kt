package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [com.am.jarvis.connector.mqtt.MqttListener].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class MqttListenerTest {

    private val topicName = "topic-name"

    @MockK(relaxUnitFun = true)
    lateinit var processor: MqttTopicMessageProcessor

    lateinit var mqttListener: MqttListener

    @BeforeEach
    fun setUp() {
        every { processor.getSupportedTopics() } returns listOf(topicName)
        mqttListener = MqttListener(listOf(processor))
    }

    @Test
    fun testConnectionLost() {
        mqttListener.connectionLost(Exception())
    }

    @Test
    fun testMessageArrived() {
        val message = MqttMessage()
        message.payload = "{\"port\": 1, \"value\": \"ON\"}".toByteArray()
        mqttListener.messageArrived(topicName, message)
        verify { processor.process(message.payload) }
    }

    @Test
    fun testMessageArrivedSkipped() {
        mqttListener.messageArrived(topicName, null)
    }

    @Test
    fun testMessageArrivedFailed() {
        every { processor.process(any()) } throws Exception()

        assertDoesNotThrow { mqttListener.messageArrived(topicName, MqttMessage()) }
    }

    @Test
    fun testDeliveryComplete() {
        val token: IMqttDeliveryToken = mockk()
        every { token.message } returns MqttMessage()
        mqttListener.deliveryComplete(token)
    }

    @Test
    fun testDeliveryCompleteTokenIsNull() {
        mqttListener.deliveryComplete(null)
    }
}
