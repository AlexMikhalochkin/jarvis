package com.mega.demo.repository

import com.mega.demo.model.Device

/**
 * Repository for devices.
 *
 * @author Alex Mikhalochkin
 */
interface DeviceRepository {

    fun findAll(): List<Device>
}
