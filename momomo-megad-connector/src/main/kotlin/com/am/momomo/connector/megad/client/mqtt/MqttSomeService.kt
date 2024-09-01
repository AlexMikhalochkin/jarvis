package com.am.momomo.connector.megad.client.mqtt

import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.DeviceState
import com.am.momomo.notifier.api.Notifier
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
        val device = deviceRepository.getDeviceByPort(portState.port)
        val deviceId = device.id
        val deviceState = DeviceState(deviceId, portState.isOn)
        val states = listOf(deviceState)
        deviceRepository.updateStates(states)
        notifiers.forEach { it.notify(deviceState) }
        val status = if (portState.isOn) "turned ON" else "turned OFF"
        logger.info { "Device [$deviceId] is $status" }
    }
}