package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceStatus

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getStatuses(listOf: List<String>): List<DeviceStatus>

    fun changeStatus(listOf: List<String>, on: Boolean)

    fun getAllDevices(): List<Device>
}
