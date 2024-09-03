package com.am.jarvis.core.api

import com.am.jarvis.core.model.Device

/**
 * Connector specific provider of the information about a [Device].
 *
 * This interface defines a contract for components that are responsible for
 * providing the list of all devices. Implementations of this interface should
 * provide the logic to retrieve the list of all available devices.
 *
 * @author Alex Mikhalochkin
 */
interface DeviceProvider {

    /**
     * Retrieves the list of all devices.
     *
     * @return a list of Device objects representing all available devices
     */
    fun getAllDevices(): List<Device>
}
