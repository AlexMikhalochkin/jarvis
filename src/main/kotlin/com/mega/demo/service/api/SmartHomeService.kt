package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getAllDevices(): List<Device>

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeState(states: List<DeviceState>): List<DeviceState>

    /**
     * Sends notification about change in device's state.
     * @param port port.
     * @param isOn is on flag.
     */
    fun sendNotification(port: Int, isOn: Boolean)
}
