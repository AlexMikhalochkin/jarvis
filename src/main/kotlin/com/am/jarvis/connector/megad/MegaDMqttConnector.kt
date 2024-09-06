package com.am.jarvis.connector.megad

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.api.DeviceProvider
import com.am.jarvis.core.api.DeviceStateChanger
import com.am.jarvis.core.api.DeviceStateProvider
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * MQTT connector for MegaD PLC.
 *
 * This class provides the implementation for retrieving all devices,
 * retrieving device states based on their IDs, and changing the states
 * of devices using MQTT messages.
 *
 * @author Alex Mikhalochkin
 */
@Component
class MegaDMqttConnector(
    private val deviceRepository: DeviceRepository,
    private val mqttClient: IMqttClient,
    @Value("\${megad.id}/cmd") private val mqttTopic: String
) : DeviceProvider, DeviceStateProvider, DeviceStateChanger {

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return deviceRepository.findStates(deviceIds)
    }

    override fun changeStates(states: List<DeviceState>): List<DeviceState> {
        states.map { toMqttMessage(it) }
            .forEach {
                logger.info("Sending message. Started. Message=$it")
                mqttClient.publish(mqttTopic, it.toByteArray(), 0, true)
                logger.info("Sending message. Finished. Message=$it")
            }
        return states
    }

    override fun getSource(): String {
        return "MegaD"
    }

    override fun areNotificationsEnabled(): Boolean {
        return true
    }

    private fun toMqttMessage(deviceState: DeviceState): String {
        val status = deviceState.intState
        val port = (
            deviceState.customData["port"] as Int?
                ?: deviceRepository.findPortByDeviceId(deviceState.deviceId)
            )
        return "$port:$status"
    }
}