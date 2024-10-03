package com.am.jarvis.connector.megad.mqtt.processor

import com.am.jarvis.connector.megad.mqtt.message.MegaDPortState
import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.api.MqttTopicMessageProcessor
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Service for processing MQTT messages.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MegaDMqttOutPortsMessageProcessor(
    @Value("\${megad.id}") private val megadId: String,
    private val deviceRepository: DeviceRepository,
    private val notifiers: List<Notifier>
) : MqttTopicMessageProcessor {

    private val mapper = jacksonObjectMapper()

    override fun process(message: ByteArray) {
        val portState = mapper.readValue(message, MegaDPortState::class.java)
        val deviceId = deviceRepository.getDeviceByPort(portState.port).id
        val deviceState = DeviceState(deviceId, portState.isOn)
        val states = listOf(deviceState)
        deviceRepository.updateStates(states)
        notifiers.forEach { it.notify(states) }
        logger.info { "Device [$deviceId] is turned ${deviceState.stringState}" }
    }

    override fun getSupportedTopics(): List<String> {
        return deviceRepository.findAllPorts()
            .map { "$megadId/$it" }
    }
}
