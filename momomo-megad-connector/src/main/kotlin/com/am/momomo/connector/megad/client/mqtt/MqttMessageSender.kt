package com.am.momomo.connector.megad.client.mqtt

import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [MessageSender] for MQTT.
 *
 * @author Alex Mikhalochkin
 */
@Component
class MqttMessageSender(val client: IMqttClient, @Value("\${megad.id}/cmd") val topic: String) : MessageSender {

    override fun send(message: String) {
        logger.info("Sending message. Started. Message=$message")
        client.publish(topic, message.toByteArray(), 0, true)
        logger.info("Sending message. Finished. Message=$message")
    }
}
