package com.am.jarvis.connector.megad.http

import com.am.jarvis.connector.megad.repository.api.DeviceRepository
import org.springframework.stereotype.Service

/**
 * Service for interacting with MegaD PLC using HTTP.
 *
 * @author Alex Mikhalochkin
 */
@Service
class MegaDHttpService(
    private val client: MegaDHttpClient,
    private val deviceRepository: DeviceRepository
) {

    /**
     * Retrieves the states of all ports.
     *
     * @return a map of port numbers to their boolean states
     */
    fun getStatesForAllPorts(): Map<Int, Boolean> {
        val portStates = client.getStatesForAllPorts().split(";")
        return deviceRepository.findAllPorts()
            .associateWith { it < portStates.size && portStates[it] == "1" }
    }
}
