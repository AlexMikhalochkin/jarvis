package com.am.momomo.connector.megad.repository.impl

import com.am.momomo.connector.megad.repository.api.DeviceRepository
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import org.springframework.stereotype.Repository

/**
 * Implementation of [DeviceRepository].
 *
 * @author Alex Mikhalochkin
 */
@Repository
internal class InMemoryDeviceRepository(
    configuredDevices: ConfiguredDevices
) : DeviceRepository {

    private val devices: List<Device> = configuredDevices.devices
    private val idsToPorts: Map<String, Int> = devices.associate { it.id to it.additionalData["port"] as Int }
    private val storedStates = mutableMapOf(
        7 to false,
        8 to false,
        9 to false,
        10 to false,
        11 to false,
        12 to false,
        13 to false,
        22 to false,
        23 to false,
        24 to false,
        25 to false,
        26 to false,
        27 to false,
        28 to false
    )

    override fun findAll(): List<Device> {
        return devices
    }

    override fun findStates(deviceIds: List<String>): List<DeviceState> {
        return deviceIds.map { it to storedStates.getValue(idsToPorts.getValue(it)) }
            .map { (deviceId, status) -> DeviceState(deviceId, status) }
            .toList()
    }

    override fun updateStates(states: List<DeviceState>) {
        val newStates = states.associate { (it.customData["port"] as Int? ?: idsToPorts[it.deviceId])!! to it.isOn }
        storedStates.putAll(newStates)
    }

    override fun getDeviceByPort(port: Int): Device {
        return devices.find { it.additionalData["port"] == port }!!
    }

    override fun findPortByDeviceId(deviceId: String): Int {
        return idsToPorts.getValue(deviceId)
    }
}
