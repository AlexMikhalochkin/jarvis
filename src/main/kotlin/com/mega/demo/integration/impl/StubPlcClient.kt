package com.mega.demo.integration.impl

import com.mega.demo.integration.api.PlcClient
import mu.KotlinLogging
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Stub implementation of [PlcClient] for development.
 *
 * @author Alex Mikhalochkin
 */
@Component
@Profile("dev")
class StubPlcClient : PlcClient {

    private val outPorts = mutableMapOf(
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

    override fun turnOn(port: Int) {
        outPorts[port] = true
        logger.info { "port $port turned on" }
    }

    override fun turnOff(port: Int) {
        outPorts[port] = false
        logger.info { "port $port turned off" }
    }

    override fun getPortStatuses(): Map<Int, Boolean> {
        return outPorts
    }
}
