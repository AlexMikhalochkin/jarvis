package com.mega.demo.service.api

import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.SmartThingsDevice

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeService {

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun executeCommands(devicesWithCommands: List<SmartThingsDevice>): List<DeviceState>

    fun getAllDevices(): List<SmartThingsDevice>
}
