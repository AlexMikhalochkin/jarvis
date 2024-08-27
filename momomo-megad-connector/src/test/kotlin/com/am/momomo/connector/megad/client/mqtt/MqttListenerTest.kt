package com.am.momomo.connector.megad.client.mqtt

import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.DeviceState
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
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
    lateinit var deviceRepository: DeviceRepository

    @InjectMockKs
    lateinit var mqttListener: MqttListener

    @Test
    fun testConnectionLost() {
        mqttListener.connectionLost(Exception())
    }

    @Test
    fun testMessageArrived() {
        val message = MqttMessage()
        message.payload = "{\"port\": 1, \"value\": true}".toByteArray()
        mqttListener.messageArrived("topic", message)
        val states = listOf(DeviceState(null, 1, true))
        verifySequence {
            deviceRepository.updateStates(states)
        }
    }

    @Test
    fun testDeliveryComplete() {
        mqttListener.deliveryComplete(null)
    }
}
