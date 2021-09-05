package com.mega.demo.integration.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.mega.demo.model.DeviceState
import com.mega.demo.service.api.SmartHomeService
import java.nio.charset.Charset
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
class MqttListener(val smartHomeService: SmartHomeService) : MqttCallback {

    private val mapper = ObjectMapper()

    override fun connectionLost(cause: Throwable?) {
        logger.error(cause) { "Connection to MQTT broker lost." }
    }

    override fun messageArrived(topic: String?, message: MqttMessage?) {
        logger.info { "Processing message. Started. Topic=$topic Message=$message" }
        val tree = mapper.readTree(message!!.payload.toString(Charset.defaultCharset()))
        val port = tree.get("port").asInt()
        val isOn = tree.get("value").asBoolean()
        val deviceState = DeviceState(null, port, isOn)
        smartHomeService.changeState(deviceState)
        logger.info { "Processing message. Finished. Topic=$topic Message=$message" }
    }

    override fun deliveryComplete(token: IMqttDeliveryToken?) {
        logger.info { "Message delivered" }
    }
}
