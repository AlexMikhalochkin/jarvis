package com.am.jarvis.service.impl

import com.am.jarvis.service.api.NotificationService
import com.am.jarvis.service.api.SmartHomeService
import com.am.momomo.connector.api.DeviceProvider
import com.am.momomo.connector.api.DeviceStateChanger
import com.am.momomo.connector.api.DeviceStateProvider
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartHomeServiceImpl(
    private val deviceProviders: List<DeviceProvider>,
    private val deviceStateProviders: List<DeviceStateProvider>,
    private val deviceStateChangers: List<DeviceStateChanger>,
    private val notificationService: NotificationService
) : SmartHomeService {

    override fun getAllDevices(): List<Device> {
        return deviceProviders.flatMap { it.getAllDevices() }
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return deviceStateProviders.flatMap { it.getDeviceStates(deviceIds) }
    }

    override fun changeStates(states: List<DeviceState>, provider: Provider): List<DeviceState> {
        deviceStateChangers.flatMap { it.changeStates(states) }
        notificationService.notifyProviders(states, provider)
        return states
    }
}
