package com.am.jarvis.connector.megad.repository.api

import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState

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
     * Finds states for specified [deviceIds].
     *
     * @return [List] of [DeviceState].
     */
    fun findStates(deviceIds: List<String>): List<DeviceState>

    /**
     * Updates stored [DeviceState] with specified [states].
     */
    fun updateStates(states: List<DeviceState>)

    /**
     * Returns [Device] by specified [port].
     */
    fun getDeviceByPort(port: Int): Device

    /**
     * Returns port by specified [deviceId].
     */
    fun findPortByDeviceId(deviceId: String): Int

    /**
     * Returns all available ports.
     */
    fun findAllPorts(): Collection<Int>
}
