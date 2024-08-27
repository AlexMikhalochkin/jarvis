package com.am.momomo.connector.megad

import com.am.momomo.connector.api.MomomoConnector
import com.am.momomo.connector.megad.client.mqtt.MessageSender
import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import org.springframework.stereotype.Component

@Component
open class MegaDMqttConnector(
    private val deviceRepository: DeviceRepository,
    private val messageSender: MessageSender
) : MomomoConnector {

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return deviceRepository.findStates(deviceIds)
    }

    override fun changeStates(states: List<DeviceState>): List<DeviceState> {
        states.forEach { sendChangeStateMessage(it.port!!, it.isOn!!) }
        return states
    }

    private fun sendChangeStateMessage(port: Int, isOn: Boolean) {
        val status = if (isOn) 1 else 0
        messageSender.send("$port:$status")
    }
}
