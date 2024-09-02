package com.am.momomo.connector.api

import com.am.momomo.model.DeviceState

/**
 * Connector specific component responsible for changing the state of a device.
 *
 * This interface defines a contract for components that are responsible for changing
 * the states of devices. Implementations of this interface should provide the logic
 * to change the states of the given devices and return the updated states.
 *
 * @author Alex Mikhalochkin
 */
interface DeviceStateChanger : SourceAware {

    /**
     * Changes the states of the given devices.
     *
     * @param states a list of DeviceState objects representing the current states of the devices
     * @return a list of DeviceState objects representing the updated states of the devices
     */
    fun changeStates(states: List<DeviceState>): List<DeviceState>

    /**
     * Checks if notifications are enabled for the device state changer.
     * Notifications are used to notify any service about the state changes.
     * Default implementation returns false.
     *
     * @return true if notifications are enabled, false otherwise.
     */
    fun areNotificationsEnabled(): Boolean {
        return false
    }
}
