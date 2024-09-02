package com.am.jarvis.service.impl

import com.am.momomo.model.Device
import org.springframework.stereotype.Component

@Component
class TestRepository {

    private lateinit var associate: Map<String, String>

    fun test(deviceIds: Collection<String>): Map<String, List<String>> {
        return deviceIds.filter { associate.contains(it) }
            .groupBy { associate[it]!! }
    }

    fun save(devices: List<Device>) {
        associate = devices.associate { it.id to it.sourceChannel }
    }
}
