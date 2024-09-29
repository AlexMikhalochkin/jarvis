package com.am.jarvis.connector.megad.mqtt

import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Publisher for sending command messages to MegaD.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MegaDMqttCommandMessagePublisher(
    private val mqttClient: IMqttClient,
    @Value("\${megad.id}/cmd") private val mqttTopic: String
) {

    fun publish(message: String) {
        logger.info("Sending message. Started. Message=$message")
        mqttClient.publish(mqttTopic, message.toByteArray(), 0, true)
        logger.info("Sending message. Finished. Message=$message")
    }
}
