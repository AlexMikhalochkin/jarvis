package com.am.momomo.connector.api

import com.am.momomo.model.DeviceState

/**
 * Connector specific provider of the information about a [DeviceState].
 *
 * This interface defines a contract for components that are responsible for
 * providing the states of devices based on their IDs. Implementations of this
 * interface should provide the logic to retrieve the states of the given devices.
 *
 * @author Alex Mikhalochkin
 */
interface DeviceStateProvider {

    /**
     * Retrieves the states of the given devices.
     *
     * @param deviceIds a list of strings representing the IDs of the devices
     * @return a list of DeviceState objects representing the current states of the devices
     */
    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>
}
