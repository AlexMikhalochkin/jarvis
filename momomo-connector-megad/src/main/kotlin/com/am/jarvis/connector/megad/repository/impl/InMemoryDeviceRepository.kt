package com.am.jarvis.connector.megad.repository.impl

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState
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

    private val devices = configuredDevices.devices
    private val idsToPorts = devices.associate { it.id to it.additionalData["port"] as Int }
    private val storedStates = devices.associate { it.additionalData["port"] as Int to false }.toMutableMap()

    override fun findAll(): List<Device> {
        return devices
    }

    override fun findStates(deviceIds: List<String>): List<DeviceState> {
        return deviceIds.associateWith { idsToPorts.getValue(it) }
            .map { (deviceId, port) -> deviceId to storedStates.getValue(port) }
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

    override fun findAllPorts(): Collection<Int> {
        return idsToPorts.values
    }
}
