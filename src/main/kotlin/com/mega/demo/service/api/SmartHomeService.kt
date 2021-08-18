package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeState(states: List<DeviceState>): List<DeviceState>

    fun getAllDevices(): List<Device>
}
