package com.mega.demo.service.impl

import com.mega.demo.integration.api.MessageSender
import com.mega.demo.integration.api.YandexCallbackClient
import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.SmartHomeService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartHomeServiceImpl(
    val deviceRepository: DeviceRepository,
    val messageSender: MessageSender,
    val yandexCallbackClient: YandexCallbackClient
) : SmartHomeService {

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        val idsToPorts = deviceRepository.findPorts(deviceIds)
        val portStatuses = getStatuses(idsToPorts.values)
        return idsToPorts
            .map { (deviceId, port) -> DeviceState(deviceId, port, portStatuses.getValue(port)) }
            .toList()
    }

    private fun getStatuses(values: Collection<Int>): Map<Int, Boolean> {
        return deviceRepository.findStatuses(values)
    }

    override fun changeState(states: List<DeviceState>): List<DeviceState> {
        states.forEach { sendChangeStateMessage(it.port!!, it.isOn!!) }
        return states
    }

    private fun sendChangeStateMessage(port: Int, isOn: Boolean) {
        val status = if (isOn) 1 else 0
        messageSender.send("$port:$status")
    }

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }

    override fun sendNotification(port: Int, isOn: Boolean) {
        yandexCallbackClient.send(deviceRepository.findIdByPort(port), isOn)
    }
}
