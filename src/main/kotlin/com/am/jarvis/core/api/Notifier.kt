package com.am.jarvis.core.api

import com.am.jarvis.core.model.DeviceState

/**
 * Notifier for notifying about device state changes.
 *
 * This interface defines a contract for components that are responsible for
 * notifying about device state changes. Implementations of this interface
 * should provide the logic to notify about the changes of the given devices.
 *
 * @author Alex Mikhalochkin
 */
interface Notifier : SourceAware {

    /**
     * Notifies about the changes of the given devices.
     *
     * @param states a list of DeviceState objects representing the current states of the devices
     */
    fun notify(states: List<DeviceState>)
}
