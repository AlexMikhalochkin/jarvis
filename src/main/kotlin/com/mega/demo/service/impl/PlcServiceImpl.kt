package com.mega.demo.service.impl

import com.mega.demo.integration.api.PlcClient
import com.mega.demo.service.api.PlcService
import org.springframework.stereotype.Service

/**
 * Implementation of [PlcService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class PlcServiceImpl(val plcClient: PlcClient) : PlcService {

    override fun turnOn(port: Int) {
        plcClient.turnOn(port)
    }

    override fun turnOff(port: Int) {
        plcClient.turnOff(port)
    }

    override fun getPortStatuses(): Map<Int, Boolean> {
        return plcClient.getPortStatuses().block().orEmpty()
    }
}
