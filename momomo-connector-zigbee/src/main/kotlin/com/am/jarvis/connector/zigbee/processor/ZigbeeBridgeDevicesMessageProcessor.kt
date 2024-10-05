package com.am.jarvis.connector.zigbee.processor

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Service for processing MQTT messages from Zigbee2Mqtt.
 *
 * @author Alex Mikhalochkin
 */
@Service
class ZigbeeBridgeDevicesMessageProcessor : MqttTopicMessageProcessor {

    override fun process(message: ByteArray) {
        logger.info { "Received message: ${String(message)}" }
    }

    override fun getSupportedTopics(): List<String> {
        return listOf("zigbee2mqtt/bridge/devices")
    }
}
