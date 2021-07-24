package com.mega.demo.controller

import com.mega.demo.service.api.PlcService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class DirectPlcController(val plcService: PlcService) {

    private val port = 7

    @GetMapping("/ports")
    fun turnOn(): Mono<Void> {
        return plcService.turnOn(port)
    }

    @GetMapping("/ports2")
    fun getStatuses(): Mono<Map<Int, String>> {
        return plcService.getPortStatuses()
    }
}
