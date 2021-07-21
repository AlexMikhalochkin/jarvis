package com.mega.demo.controller

import com.mega.demo.integration.MegaDClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class DirectPlcController(val megaDClient: MegaDClient) {

    @GetMapping("/ports")
    fun turnOn(): Mono<Void> {
        return megaDClient.turnOn(7)
    }

    @GetMapping("/ports2")
    fun getStatuses(): Mono<Map<Int, String>> {
        return megaDClient.getPortStatuses()
    }
}