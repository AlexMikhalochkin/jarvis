package com.mega.demo.integration.api

import reactor.core.publisher.Mono

/**
 * Http client for programmable logic controller.
 *
 * @author Alex Mikhalochkin
 */
interface PlcClient {

    fun turnOn(port: Int): Mono<Void>

    fun turnOff(port: Int): Mono<Void>

    fun getPortStatuses(): Mono<Map<Int, String>>
}
