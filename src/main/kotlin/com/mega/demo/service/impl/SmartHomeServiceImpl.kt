package com.mega.demo.service.impl

import com.mega.demo.integration.api.MessageSender
import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider
import com.mega.demo.repository.api.DeviceRepository
import com.mega.demo.service.api.NotificationService
import com.mega.demo.service.api.SmartHomeService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartHomeServiceImpl(
    private val deviceRepository: DeviceRepository,
    private val messageSender: MessageSender,
    private val notificationService: NotificationService
) : SmartHomeService {

    override fun getAllDevices(): List<Device> {
        return deviceRepository.findAll()
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return deviceRepository.findStates(deviceIds)
    }

    override fun changeStates(states: List<DeviceState>, provider: Provider): List<DeviceState> {
        deviceRepository.updateStates(states)
        states.forEach { sendChangeStateMessage(it.port!!, it.isOn!!) }
        notificationService.notifyProviders(states, provider)
        return states
    }

    private fun sendChangeStateMessage(port: Int, isOn: Boolean) {
        val status = if (isOn) 1 else 0
        messageSender.send("$port:$status")
    }
}
