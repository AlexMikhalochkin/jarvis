package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState
import com.mega.demo.model.Provider

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getAllDevices(): List<Device>

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeStates(states: List<DeviceState>, provider: Provider): List<DeviceState>

    fun changeState(deviceState: DeviceState)
}
