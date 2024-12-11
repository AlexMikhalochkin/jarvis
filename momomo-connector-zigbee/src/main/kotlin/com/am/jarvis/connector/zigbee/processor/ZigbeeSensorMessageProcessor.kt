package com.am.jarvis.connector.zigbee.processor

import com.am.jarvis.core.api.MqttTopicMessageProcessor
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service

/**
 * Service for processing MQTT messages from Zigbee2Mqtt.
 *
 * @author Alex Mikhalochkin
 */
@Service
class ZigbeeSensorMessageProcessor(
    private val notifiers: List<Notifier>
) : MqttTopicMessageProcessor {

    private val mapper = jacksonObjectMapper()

    override fun process(message: ByteArray) {
        val device: ZigbeeDevice = mapper.readValue(message, ZigbeeDevice::class.java)
        val customData = mapOf(
            "temperature" to device.temperature!!,
            "humidity" to device.humidity!!,
            "battery" to device.battery!!
        )
        DeviceState("child-sensor-1", false, customData).let { state ->
            notifiers.forEach { it.notify(listOf(state), false) }
        }
    }

    override fun getSupportedTopics(): List<String> {
        return listOf("zigbee2mqtt/button")
    }
}
