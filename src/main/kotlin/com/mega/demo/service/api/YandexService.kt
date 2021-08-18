package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.DeviceState

/**
 * Yandex service.
 *
 * @author Alex Mikhalochkin
 */
interface YandexService {

    fun getDeviceStates(deviceIds: List<String>): List<DeviceState>

    fun changeState(devicesWithStates: List<DeviceState>): List<DeviceState>

    fun getAllDevices(): List<Device>
}
