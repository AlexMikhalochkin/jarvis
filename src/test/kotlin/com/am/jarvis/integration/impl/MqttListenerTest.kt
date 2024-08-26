package com.am.jarvis.integration.impl

import com.am.jarvis.integration.impl.megad.mqtt.MqttListener
import com.am.jarvis.model.DeviceState
import com.am.jarvis.repository.api.DeviceRepository
import com.am.jarvis.service.api.NotificationService
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

    @MockK(relaxUnitFun = true)
    lateinit var notificationService: NotificationService

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
            notificationService.notifyProviders(states)
        }
    }

    @Test
    fun testDeliveryComplete() {
        mqttListener.deliveryComplete(null)
    }
}
