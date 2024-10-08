package com.am.jarvis.service.impl

import com.am.jarvis.core.model.Device
import com.am.jarvis.service.api.DeviceSourceRepository
import org.springframework.stereotype.Component

@Component
class InMemoryDeviceSourceRepository : DeviceSourceRepository {

    private val deviceIdToSourceChannel: MutableMap<String, String> = mutableMapOf()

    override fun save(devices: Collection<Device>) {
        deviceIdToSourceChannel.putAll(devices.associate { it.id to it.sourceChannel })
    }

    override fun getDevicesPerSource(deviceIds: Collection<String>): Map<String, List<String>> {
        return deviceIds.filter { deviceIdToSourceChannel.contains(it) }
            .groupBy { deviceIdToSourceChannel.getValue(it) }
    }
}
