package com.am.jarvis.connector.megad.http

import com.am.jarvis.connector.megad.StatesRefresher
import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import com.am.jarvis.core.api.Notifier
import com.am.jarvis.core.model.Device
import com.am.jarvis.core.model.DeviceState
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

/**
 * Implementation of [StatesRefresher] for HTTP type of integration with MegaD.
 *
 * @author Alex Mikhalochkin
 */
@Primary
@Service
class HttpStatesRefresher(
    private val deviceRepository: DeviceRepository,
    private val megaDHttpService: MegaDHttpService,
    private val notifiers: List<Notifier>
) : StatesRefresher {

    override fun run() {
        val allStates = megaDHttpService.getStatesForAllPorts()
        val states = deviceRepository.findAll()
            .map { toState(it, allStates) }
        notifiers.forEach { it.notify(states) }
    }

    private fun toState(device: Device, allStates: Map<Int, Boolean>): DeviceState {
        return DeviceState(
            device.id,
            allStates.getValue(device.additionalData["port"] as Int)
        )
    }
}
