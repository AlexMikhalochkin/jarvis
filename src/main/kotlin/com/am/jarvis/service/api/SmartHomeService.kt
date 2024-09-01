package com.am.jarvis.service.api

import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getAllDevices(): List<Device>

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeStates(states: List<DeviceState>, source: String): List<DeviceState>
}
