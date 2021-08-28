package com.mega.demo.integration.impl

import com.mega.demo.integration.api.MessageSender
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [MessageSender] for MQTT.
 *
 * @author Alex Mikhalochkin
 */
@Component
class MqttMessageSender(val client: IMqttClient) : MessageSender {

    private val topic = "megad/14/cmd"

    override fun send(message: String) {
        logger.info("Sending message. Started. Message=$message")
        client.publish(topic, message.toByteArray(), 0, true)
        logger.info("Sending message. Finished. Message=$message")
    }
}
