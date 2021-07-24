package com.mega.demo.service.impl

import com.mega.demo.integration.api.PlcClient
import com.mega.demo.service.api.PlcService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Implementation of [PlcService].
 *
 * @author Alex Mikhalochkin
 */
@Service
class PlcServiceImpl(val plcClient: PlcClient) : PlcService {

    override fun turnOn(port: Int): Mono<Void> {
       return plcClient.turnOn(port)
    }

    override fun turnOff(port: Int): Mono<Void> {
        return plcClient.turnOff(port)
    }

    override fun getPortStatuses(): Mono<Map<Int, String>> {
        return plcClient.getPortStatuses()
    }
}
