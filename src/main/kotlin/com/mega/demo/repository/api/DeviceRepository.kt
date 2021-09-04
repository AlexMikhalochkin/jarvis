package com.mega.demo.repository.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState

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
     * Finds id of the [Device] by specified [port].
     *
     * @return id of the [Device].
     */
    fun findIdByPort(port: Int): String

    /**
     * Updates stored [DeviceState] with specified [states].
     */
    fun updateStates(states: List<DeviceState>)
}
