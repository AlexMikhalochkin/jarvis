package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttMessagePublisher
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class CommonMqttMessagePublisher(
    @Qualifier("mqttClientPublisher") private val mqttClientPublisher: IMqttClient,
) : MqttMessagePublisher {

    override fun publish(topic: String, message: String) {
        logger.info("Sending message. Started. Topic=$topic. Message=$message")
        mqttClientPublisher.publish(topic, message.toByteArray(), 0, true)
        logger.info("Sending message. Finished. Topic=$topic. Message=$message")
    }
}
