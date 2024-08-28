package com.am.momomo.connector.megad.client.mqtt

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.stereotype.Component
import java.nio.charset.Charset

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

    private val mapper = ObjectMapper()

    override fun connectionLost(cause: Throwable?) {
        logger.error(cause) { "Connection to MQTT broker lost." }
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        logger.debug { "Processing message. Started. Topic=$topic Message=$message" }
        val tree = mapper.readTree(message!!.payload.toString(Charset.defaultCharset()))
        val port = tree["port"].asInt()
        val isOn = tree["value"].asBoolean()
        service.process(port, isOn)
        logger.debug { "Processing message. Finished. Topic=$topic Message=$message" }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        logger.info { "Message delivered" }
    }
}
