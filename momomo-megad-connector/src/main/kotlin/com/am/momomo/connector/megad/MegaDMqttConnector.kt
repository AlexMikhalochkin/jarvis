package com.am.momomo.connector.megad

import com.am.momomo.connector.api.DeviceProvider
import com.am.momomo.connector.api.DeviceStateChanger
import com.am.momomo.connector.api.DeviceStateProvider
import com.am.momomo.connector.megad.client.mqtt.MessageSender
import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import org.springframework.stereotype.Component

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
open class MegaDMqttConnector(
    private val deviceRepository: DeviceRepository,
    private val messageSender: MessageSender
) : DeviceProvider, DeviceStateProvider, DeviceStateChanger {

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return deviceRepository.findStates(deviceIds)
    }

    override fun changeStates(states: List<DeviceState>): List<DeviceState> {
        states.map { toMqttMessage(it) }
            .forEach { messageSender.send(it) }
        return states
    }

    private fun toMqttMessage(deviceState: DeviceState): String {
        val status = if (deviceState.isOn!!) 1 else 0
        val port = (deviceState.customData["port"] as Int?
            ?: deviceRepository.findPortByDeviceId(deviceState.deviceId!!))
        return "$port:$status"
    }
}
