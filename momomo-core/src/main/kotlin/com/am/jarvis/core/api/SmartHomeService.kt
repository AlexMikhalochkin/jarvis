package com.am.jarvis.core.api

import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState

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
