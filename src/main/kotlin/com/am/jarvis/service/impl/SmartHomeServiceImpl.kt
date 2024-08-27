package com.am.jarvis.service.impl

import com.am.jarvis.service.api.NotificationService
import com.am.jarvis.service.api.SmartHomeService
import com.am.momomo.connector.api.MomomoConnector
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
    private val connectors: List<MomomoConnector>,
    private val notificationService: NotificationService
) : SmartHomeService {

    override fun getAllDevices(): List<Device> {
        return connectors.flatMap { it.getAllDevices() }
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return connectors.flatMap { it.getDeviceStates(deviceIds) }
    }

    override fun changeStates(states: List<DeviceState>, provider: Provider): List<DeviceState> {
        connectors.flatMap { it.changeStates(states) }
        notificationService.notifyProviders(states, provider)
        return states
    }
}
