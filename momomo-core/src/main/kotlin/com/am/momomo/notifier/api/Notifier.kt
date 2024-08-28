package com.am.momomo.notifier.api

import com.am.momomo.model.DeviceState

/**
 * Notifier for notifying about device state changes.
 *
 * This interface defines a contract for components that are responsible for
 * notifying about device state changes. Implementations of this interface
 * should provide the logic to notify about the changes of the given devices.
 *
 * @author Alex Mikhalochkin
 */
interface Notifier {

    /**
     * Notifies about the given device state.
     *
     * @param state a [DeviceState] object representing the current state of the device
     */
    fun notify(state: DeviceState)
}
