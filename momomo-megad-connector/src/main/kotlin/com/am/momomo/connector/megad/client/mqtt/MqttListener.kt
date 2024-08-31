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

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        logger.debug { "Processing message. Started. Topic=$topic Message=$message" }
        if (message == null) {
            logger.warn { "Processing message. Skipped. Message is empty. Topic=$topic" }
            return
        }
        val megaDPortState = megaDPortState(message.payload)
        service.process(megaDPortState)
        logger.debug { "Processing message. Finished. Topic=$topic Message=$message" }
    }

    private fun megaDPortState(bytes: ByteArray?): MegaDPortState =
        mapper.readValue(bytes, MegaDPortState::class.java)

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        logger.info { "Message delivered" }
    }
}
