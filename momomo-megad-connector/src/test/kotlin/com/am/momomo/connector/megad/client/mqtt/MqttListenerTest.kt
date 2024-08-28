package com.am.momomo.connector.megad.client.mqtt

import io.mockk.Called
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

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

    @Test
    fun testMessageArrived() {
        val message = MqttMessage()
        message.payload = "{\"port\": 1, \"value\": true}".toByteArray()
        mqttListener.messageArrived("topic", message)
        verify { service.process(1, true) }
    }

    @Test
    fun testDeliveryComplete() {
        mqttListener.deliveryComplete(null)

        verify { service wasNot Called }
    }
}
