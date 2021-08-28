package com.mega.demo.service.impl

import com.mega.demo.integration.PortStatusMessage
import com.mega.demo.service.api.MessageSender
import java.util.Locale
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Implementation of [MessageSender] for MQTT.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MqttMessageSender(val client: IMqttClient) : MessageSender {

    private val topic = "megad/14/cmd"

    override fun send(message: PortStatusMessage) {
        logger.info("Sending message. Started. Message=$message")
        val mqttMessage = String.format(Locale.getDefault(), "%d:%d", message.port, if (message.status!!) 1 else 0)
        client.publish(topic, mqttMessage.toByteArray(), 0, true)
        logger.info("Sending message. Finished. Message=$message")
    }
}
