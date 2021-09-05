package com.mega.demo.service.impl

import com.mega.demo.integration.api.SmartHomeProviderClient
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider
import com.mega.demo.service.api.NotificationService
import org.springframework.stereotype.Service

/**
 * Implementation of [NotificationService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class NotificationServiceImpl(private val smartHomeProviderClients: List<SmartHomeProviderClient>) :
    NotificationService {

    override fun notifyProviders(states: List<DeviceState>) {
        smartHomeProviderClients.forEach { it.updateStates(states) }
    }

    override fun notifyProviders(states: List<DeviceState>, exclude: Provider) {
        smartHomeProviderClients.filter { it.getProvider() != exclude }
            .forEach { it.updateStates(states) }
    }
}
