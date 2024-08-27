package com.am.momomo.connector.megad.repository.api

import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState

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
     * Finds ports for specified [deviceIds].
     *
     * @return [Map] where key is id and value is port of this [Device].
     */
    fun findPorts(deviceIds: List<String>): Map<String, Int>

    /**
     * Finds states for specified [deviceIds].
     *
     * @return [List] of [DeviceState].
     */
    fun findStates(deviceIds: List<String>): List<DeviceState>

    /**
     * Updates stored [DeviceState] with specified [states].
     */
    fun updateStates(states: List<DeviceState>)
}
