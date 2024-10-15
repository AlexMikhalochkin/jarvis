package com.am.jarvis.connector.zigbee.processor

import com.am.jarvis.core.api.MqttMessagePublisher
import com.am.jarvis.core.api.MqttTopicMessageProcessor
import com.fasterxml.jackson.core.type.TypeReference
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
    @Value("\${megad.id}/cmd") private val mqttTopic: String
) : MqttTopicMessageProcessor {

    private val mapper = jacksonObjectMapper()

    override fun process(message: ByteArray) {
        val devices: ZigbeeDevice = mapper.readValue(message, ZigbeeDevice::class.java)
        if ("double" == devices.action) {
            publisher.publish(mqttTopic, "13:2")
        } else  {
            publisher.publish(mqttTopic, "22:2")
        }
    }

    override fun getSupportedTopics(): List<String> {
        return listOf("zigbee2mqtt/button")
    }
}