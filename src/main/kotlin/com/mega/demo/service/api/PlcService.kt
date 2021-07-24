package com.mega.demo.service.api

import reactor.core.publisher.Mono

/**
 * Service for programmable logic controller.
 *
 * @author Alex Mikhalochkin
 */
interface PlcService {

    fun turnOn(port: Int): Mono<Void>

    fun turnOff(port: Int): Mono<Void>

    fun getPortStatuses(): Mono<Map<Int, String>>
}
