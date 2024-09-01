package com.am.jarvis.service.impl

import com.am.jarvis.service.api.SmartHomeService
import com.am.momomo.connector.api.DeviceProvider
import com.am.momomo.connector.api.DeviceStateChanger
import com.am.momomo.connector.api.DeviceStateProvider
import com.am.momomo.model.Device
import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider
import com.am.momomo.notifier.api.Notifier
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
    private val repository: TestRepository
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
        return repository.test(deviceIds)
            .map { (source, deviceIdsPerSource) -> sourceToDeviceStateProvider[source]!! to deviceIdsPerSource }
            .flatMap { (provider, deviceIdsPerSource) -> provider.getDeviceStates(deviceIdsPerSource) }
            .toList()
    }

    override fun changeStates(states: List<DeviceState>, provider2: Provider): List<DeviceState> {
        val deviceIdToDeviceState = states.associateBy { it.deviceId }
        return repository.test(deviceIdToDeviceState.keys)
            .map { (source, deviceIdsPerSource) -> source to deviceIdsPerSource.map { deviceIdToDeviceState[it]!! } }
            .map { (source, statesPerSource) -> sourceToDeviceStateChanger[source]!! to statesPerSource }
            .flatMap { (provider, statesPerSource) -> deviceStates(provider, provider2, statesPerSource) }
            .toList()
    }

    private fun deviceStates(
        provider: DeviceStateChanger,
        provider2: Provider,
        statesPerSource: List<DeviceState>
    ): List<DeviceState> {
        val changedStates = provider.changeStates(statesPerSource)
        notifiers.filter { it.getSource() != provider.getSource() && it.getSource() != provider2.toString() }
            .forEach { it.notify(statesPerSource) }
        return changedStates
    }
}
