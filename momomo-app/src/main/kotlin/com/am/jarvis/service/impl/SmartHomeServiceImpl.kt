package com.am.jarvis.service.impl

import com.am.jarvis.core.api.DeviceProvider
import com.am.jarvis.core.api.DeviceStateChanger
import com.am.jarvis.core.api.DeviceStateProvider
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState
import com.am.jarvis.service.api.DeviceSourceRepository
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.stereotype.Service

/**
 * Implementation of [SmartHomeService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class SmartHomeServiceImpl(
    private val deviceProviders: List<DeviceProvider>,
    deviceStateProviders: List<DeviceStateProvider>,
    deviceStateChangers: List<DeviceStateChanger>,
    private val notifiers: List<Notifier>,
    private val repository: DeviceSourceRepository
) : SmartHomeService {

    private val sourceToDeviceStateProvider = deviceStateProviders.associateBy { it.getSource() }
    private val sourceToDeviceStateChanger = deviceStateChangers.associateBy { it.getSource() }

    init {
        repository.save(deviceProviders.flatMap { it.getAllDevices() })
    }

    override fun getAllDevices(): List<Device> {
        val devices = deviceProviders.flatMap { it.getAllDevices() }
        repository.save(devices)
        return devices
    }

    override fun getDeviceStates(deviceIds: List<String>): List<DeviceState> {
        return repository.getDevicesPerSource(deviceIds)
            .map { (source, deviceIdsPerSource) -> sourceToDeviceStateProvider.getValue(source) to deviceIdsPerSource }
            .flatMap { (provider, deviceIdsPerSource) -> provider.getDeviceStates(deviceIdsPerSource) }
            .toList()
    }

    override fun changeStates(states: List<DeviceState>, source: String): List<DeviceState> {
        val deviceIdToDeviceState = states.associateBy { it.deviceId }
        return repository.getDevicesPerSource(deviceIdToDeviceState.keys)
            .map { (source, idsPerSource) -> source to idsPerSource.mapNotNull { deviceIdToDeviceState[it] } }
            .map { (source, statesPerSource) -> sourceToDeviceStateChanger.getValue(source) to statesPerSource }
            .flatMap { (provider, statesPerSource) -> changeStates(provider, source, statesPerSource) }
            .toList()
    }

    private fun changeStates(
        stateChanger: DeviceStateChanger,
        requestSource: String,
        statesPerSource: List<DeviceState>
    ): List<DeviceState> {
        val changedStates = stateChanger.changeStates(statesPerSource)
        if (stateChanger.areNotificationsEnabled()) {
            return changedStates
        }
        notifiers.filterNot { it.getSource() == stateChanger.getSource() }
            .filterNot { it.getSource() == requestSource }
            .forEach { it.notify(statesPerSource) }
        return changedStates
    }
}
