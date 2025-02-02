package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.connector.megad.StatesRefresher
import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Implementation of [StatesRefresher] for MQTT type of integration with MegaD.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MqttStatesRefresher(
    private val deviceRepository: DeviceRepository,
    private val commandPublisher: MegaDMqttCommandMessagePublisher,
    @Value("\${megad.states.interval-between-messages-seconds}") private val intervalBetweenMessages: Long
) : StatesRefresher {

    override fun run() {
        deviceRepository.findAllPorts()
            .map { "get:$it" }
            .forEach(this::publishWithDelay)
    }

    @Suppress("MagicNumber")
    private fun publishWithDelay(message: String) {
        Thread.sleep(intervalBetweenMessages * 1000)
        commandPublisher.publish(message)
    }
}
