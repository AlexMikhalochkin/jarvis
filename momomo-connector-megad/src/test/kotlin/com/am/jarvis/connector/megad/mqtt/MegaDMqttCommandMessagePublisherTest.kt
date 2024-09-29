package com.am.jarvis.connector.megad.mqtt

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Verification for [MegaDMqttCommandMessagePublisher].
 *
 * @author Alex Mikhalochkin
 */
@ExtendWith(MockKExtension::class)
class MegaDMqttCommandMessagePublisherTest {

    @MockK(relaxUnitFun = true)
    private lateinit var mqttClient: IMqttClient

    private lateinit var publisher: MegaDMqttCommandMessagePublisher

    @BeforeEach
    fun setUp() {
        publisher = MegaDMqttCommandMessagePublisher(mqttClient, "topic/cmd")
    }

    @Test
    fun publishTest() {
        publisher.publish("2:0")

        verify { mqttClient.publish("topic/cmd", "2:0".toByteArray(), 0, true) }
    }
}
