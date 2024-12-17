package com.am.jarvis.connector.zigbee.processor

import com.am.jarvis.core.api.MqttMessagePublisher
import com.am.jarvis.core.api.MqttTopicMessageProcessor
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Service for processing MQTT messages from Zigbee2Mqtt.
 *
 * @author Alex Mikhalochkin
 */
@Service
class ZigbeeButtonMessageProcessor(
    private val publisher: MqttMessagePublisher,
    @Value("\${megad.id}/cmd") private val mqttTopic: String,
    private val notifiers: List<Notifier>
) : MqttTopicMessageProcessor {

    private val mapper = jacksonObjectMapper()

    override fun process(message: ByteArray) {
        val device: ZigbeeDevice = mapper.readValue(message, ZigbeeDevice::class.java)
        val action = device.action
        if (action.isNullOrEmpty()) {
            return
        }
        val customData: MutableMap<String, Any> = mutableMapOf()
        if ("double" == action) {
            publisher.publish(mqttTopic, "13:2")
            customData["button"] = "double_click"
        } else {
            publisher.publish(mqttTopic, "22:2")
            customData["button"] = "click"
        }
        device.battery?.let { customData["battery"] = it }
        device.voltage?.let { customData["voltage"] = it }
        DeviceState("child-button-0", false, customData).let { state ->
            notifiers.forEach { it.notify(listOf(state), false) }
        }
    }

    override fun getSupportedTopics(): List<String> {
        return listOf("zigbee2mqtt/button")
    }
}
