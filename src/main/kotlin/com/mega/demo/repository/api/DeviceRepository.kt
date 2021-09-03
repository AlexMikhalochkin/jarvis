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
     * Finds ports for specified [deviceIds].
     *
     * @return [Map] where key is id and value is port of this [Device].
     */
    fun findPorts(deviceIds: List<String>): Map<String, Int>

    /**
     * Finds statuses for specified [ports].
     *
     * @return [Map] where key is port and value is its status.
     */
    fun findStatuses(ports: Collection<Int>): Map<Int, Boolean>

    /**
     * Finds id of the [Device] by specified [port].
     *
     * @return id of the [Device].
     */
    fun findIdByPort(port: Int): String
}
