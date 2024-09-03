package com.am.jarvis.connector.megad.mqtt

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.DeviceState
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

/**
 * Service for processing MQTT messages.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MqttSomeService(
    private val deviceRepository: DeviceRepository,
    private val notifiers: List<Notifier>
) {

    fun process(portState: MegaDPortState) {
        val deviceId = deviceRepository.getDeviceByPort(portState.port).id
        val deviceState = DeviceState(deviceId, portState.isOn)
        val states = listOf(deviceState)
        deviceRepository.updateStates(states)
        notifiers.forEach { it.notify(states) }
        logger.info { "Device [$deviceId] is turned ${deviceState.stringState}" }
    }
}
