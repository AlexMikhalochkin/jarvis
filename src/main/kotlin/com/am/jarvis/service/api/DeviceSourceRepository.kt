package com.am.jarvis.service.api

import com.am.jarvis.core.model.Device

/**
 * Interface for a repository that handles device sources.
 *
 * @author Alex Mikhalochkin
 */
interface DeviceSourceRepository {

    /**
     * Saves a collection of devices.
     *
     * @param devices A collection of Device objects to be saved.
     */
    fun save(devices: Collection<Device>)

    /**
     * Returns a map of device sources for the given device IDs.
     *
     * @param deviceIds A collection of device IDs.
     * @return A map of device sources for the given device IDs.
     */
    fun getDevicesPerSource(deviceIds: Collection<String>): Map<String, List<String>>
}
