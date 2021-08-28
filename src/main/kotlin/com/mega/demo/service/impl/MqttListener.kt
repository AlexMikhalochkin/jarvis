package com.mega.demo.service.impl

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
class MqttListener : MqttCallback {

    override fun connectionLost(cause: Throwable?) {
        logger.error { "Connection lost" }
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        logger.info { "Processing message. Started. Topic=$topic Message=$message" }
        logger.info { "Processing message. Finished. Topic=$topic Message=$message" }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        logger.info { "Message delivered" }
    }
}
