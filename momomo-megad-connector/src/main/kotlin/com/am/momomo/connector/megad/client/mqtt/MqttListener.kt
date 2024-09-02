package com.am.momomo.connector.megad.client.mqtt

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Listener for MQTT topic.
 *
 * @author Alex Mikhalochkin
 */
@Component
internal class MqttListener(
    private val service: MqttSomeService
) : MqttCallback {

    private val mapper = jacksonObjectMapper()

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
            val megaDPortState = mapper.readValue(message.payload, MegaDPortState::class.java)
            service.process(megaDPortState)
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
