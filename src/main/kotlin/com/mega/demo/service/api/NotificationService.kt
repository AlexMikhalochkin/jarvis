package com.mega.demo.service.api

import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider

/**
 * Service for sending notifications to smart home providers.
 *
 * @author Alex Mikhalochkin
 */
interface NotificationService {

    /**
     * Notifies smart home providers about changes in devices' states.
     *
     * @param states  [List] of [DeviceState].
     */
    fun notifyProviders(states: List<DeviceState>)

    /**
     * Notifies smart home providers about changes in devices' states.
     *
     * @param states  [List] of [DeviceState].
     * @param exclude  provider that should not be notified.
     */
    fun notifyProviders(states: List<DeviceState>, exclude: Provider)
}
