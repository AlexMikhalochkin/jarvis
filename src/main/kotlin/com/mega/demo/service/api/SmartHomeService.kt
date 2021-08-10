package com.mega.demo.service.api

/**
 * Smart home service.
 *
 * @author Alex Mikhalochkin
 *
 * @param D the type of smart device.
 * @param S the type of smart device's state.
 */
interface SmartHomeService<D, S> {

    fun getDeviceStates(deviceIds: List<String>): List<S>

    fun changeState(devicesWithStates: List<D>): List<S>

    fun getAllDevices(): List<D>
}
