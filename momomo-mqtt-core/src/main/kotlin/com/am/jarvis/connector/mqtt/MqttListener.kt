package com.am.jarvis.connector.mqtt

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Listener for MQTT topics.
 *
 * @author Alex Mikhalochkin
 */
@Component
@MosquitoEnabled
internal class MqttListener(
    processors: List<MqttTopicMessageProcessor>
) : MqttCallback {

    @PostConstruct
    fun init() {
        logger.info { "MQTT listener started" }
    }

    private val processorsForTopic = processors.map { it.getSupportedTopics() to it }
        .flatMap { (topics, processor) -> topics.map { it to processor } }
        .toMap()

    override fun connectionLost(cause: Throwable?) {
        logger.error(cause) { "Connection to MQTT broker lost." }
    }

    @Suppress("TooGenericExceptionCaught")
    override fun messageArrived(topic: String?, message: MqttMessage?) {
        logger.debug { "Processing message. Started. Topic=$topic, Message=$message" }
        if (message == null) {
            logger.warn { "Processing message. Skipped. Message is empty. Topic=$topic" }
            return
        }
        try {
            processorsForTopic[topic]?.process(message.payload)
            logger.debug { "Processing message. Finished. Topic=$topic, Message=$message" }
        } catch (e: Exception) {
            logger.error(e) { "Processing message. Failed. Topic=$topic, Message=$message" }
        }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        val message = token?.message
        logger.info { "Message [$message] was delivered" }
    }
}
