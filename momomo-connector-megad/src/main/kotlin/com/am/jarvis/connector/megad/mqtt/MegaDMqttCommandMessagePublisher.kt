package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.core.api.MqttMessagePublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Publisher for sending command messages to MegaD.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MegaDMqttCommandMessagePublisher(
    private val publisher: MqttMessagePublisher,
    @Value("\${megad.id}/cmd") private val mqttTopic: String
) {

    fun publish(message: String) {
        publisher.publish(mqttTopic, message)
    }
}
