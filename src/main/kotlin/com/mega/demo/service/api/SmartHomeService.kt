package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceStatus

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getStatuses(deviceIds: List<String>): List<DeviceStatus>

    fun changeStatus(listOf: List<String>, on: Boolean): List<DeviceStatus>

    fun getAllDevices(): List<Device>
}
