package com.am.jarvis.service.impl

import com.am.jarvis.integration.api.SmartHomeProviderClient
import com.am.momomo.model.Provider
import com.am.jarvis.service.api.NotificationService
import com.am.momomo.model.DeviceState
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
