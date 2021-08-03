package com.mega.demo.repository.api

import com.mega.demo.model.Device

/**
 * Repository for devices.
 *
 * @author Alex Mikhalochkin
 */
interface DeviceRepository {

    /**
     * Finds all available devices.
     *
     * @return [List] of [Device].
     */
    fun findAll(): List<Device>

    /**
     * Finds all available devices.
     *
     * @param deviceIds [List] of ids.
     * @return [Map] where key is id and value is port of this device.
     */
    fun findPorts(deviceIds: List<String>): Map<String, Int>
}
