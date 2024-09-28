package com.am.jarvis.connector.megad.mqtt

import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * Verification for [MqttListener].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
internal class MqttListenerTest {

    @MockK(relaxUnitFun = true)
    lateinit var service: MqttSomeService

    @InjectMockKs
    lateinit var mqttListener: MqttListener

    @Test
    fun testConnectionLost() {
        mqttListener.connectionLost(Exception())

        verify { service wasNot Called }
    }

    @ParameterizedTest
    @CsvSource(
        "ON, true",
        "OFF, false"
    )
    fun testMessageArrived(messageState: String, expectedState: Boolean) {
        val message = MqttMessage()
        message.payload = "{\"port\": 1, \"value\": \"$messageState\"}".toByteArray()
        mqttListener.messageArrived("topic", message)
        verify { service.process(MegaDPortState(1, expectedState)) }
    }

    @Test
    fun testMessageArrivedSkipped() {
        mqttListener.messageArrived("topic", null)

        verify { service wasNot Called }
    }

    @Test
    fun testMessageArrivedFailed() {
        every { service.process(any()) } throws Exception()

        assertDoesNotThrow { mqttListener.messageArrived("topic", MqttMessage()) }
    }

    @Test
    fun testDeliveryComplete() {
        val token: IMqttDeliveryToken = mockk()
        every { token.message } returns MqttMessage()
        mqttListener.deliveryComplete(token)

        verify { service wasNot Called }
    }

    @Test
    fun testDeliveryCompleteTokenIsNull() {
        mqttListener.deliveryComplete(null)

        verify { service wasNot Called }
    }
}
