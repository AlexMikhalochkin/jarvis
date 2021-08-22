package com.mega.demo.controller

import com.mega.demo.service.api.PlcService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DirectPlcController(val plcService: PlcService) {

    private val port = 7

    @GetMapping("/ports")
    fun turnOn() {
        plcService.turnOn(port)
    }

    @GetMapping("/ports2")
    fun getStatuses(): Map<Int, Boolean> {
        return plcService.getPortStatuses()
    }
}
