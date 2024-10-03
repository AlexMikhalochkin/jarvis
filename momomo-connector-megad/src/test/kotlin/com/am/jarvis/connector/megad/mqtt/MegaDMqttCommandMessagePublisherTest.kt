package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.core.api.MqttMessagePublisher
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
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
    private lateinit var mqttPublisher: MqttMessagePublisher

    private lateinit var publisher: MegaDMqttCommandMessagePublisher

    @BeforeEach
    fun setUp() {
        publisher = MegaDMqttCommandMessagePublisher(mqttPublisher, "topic/cmd")
    }

    @Test
    fun publishTest() {
        publisher.publish("2:0")

        verify { mqttPublisher.publish("topic/cmd", "2:0") }
    }
}
