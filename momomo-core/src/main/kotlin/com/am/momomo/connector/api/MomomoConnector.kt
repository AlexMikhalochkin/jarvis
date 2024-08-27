package com.am.momomo.connector.api

import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState

interface MomomoConnector {

    fun getAllDevices(): List<Device>

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeStates(states: List<DeviceState>): List<DeviceState>
}
