package com.am.jarvis.service.impl

import com.am.jarvis.integration.api.MessageSender
import com.am.jarvis.model.Device
import com.am.jarvis.model.DeviceState
import com.am.jarvis.model.Provider
import com.am.jarvis.repository.api.DeviceRepository
import com.am.jarvis.service.api.NotificationService
import com.am.jarvis.service.api.SmartHomeService
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
