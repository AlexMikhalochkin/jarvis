package com.mega.demo.service.api

import com.mega.demo.controller.generated.model.DeviceState
import com.mega.demo.controller.generated.model.SmartThingsDevice

/**
 * SmartThings service.
 *
 * @author Alex Mikhalochkin
 */
interface SmartThingsService {

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeState(devicesWithStates: List<SmartThingsDevice>): List<DeviceState>

    fun getAllDevices(): List<SmartThingsDevice>
}
