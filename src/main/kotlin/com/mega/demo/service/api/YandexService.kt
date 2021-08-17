package com.mega.demo.service.api

import com.mega.demo.model.Device
import com.mega.demo.model.yandex.DeviceStateHolder

/**
 * Yandex service.
 *
 * @author Alex Mikhalochkin
 */
interface YandexService {

    fun getDeviceStates(deviceIds: List<String>): List<DeviceStateHolder>

    fun changeState(devicesWithStates: List<DeviceStateHolder>): List<DeviceStateHolder>

    fun getAllDevices(): List<Device>
}
