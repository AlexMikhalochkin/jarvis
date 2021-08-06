package com.mega.demo.controller

import com.mega.demo.controller.model.smartthings.Headers
import com.mega.demo.controller.model.smartthings.SmartThingsRequest
import com.mega.demo.controller.model.smartthings.SmartThingsResponse
import com.mega.demo.service.api.SmartHomeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * SmartThings controller.
 *
 * @author Alex Mikhalochkin
 */
@RestController
class SmartThingsController(val smartThingsService: SmartHomeService) {

    private val requestHandlers = mapOf(
        "discoveryRequest" to ::handleDiscoveryRequest,
        "stateRefreshRequest" to ::handleStateRefreshRequest,
        "commandRequest" to ::handleCommandRequest
    )

    @PostMapping(path = ["/smartthings"])
    fun handle(@RequestBody request: SmartThingsRequest): SmartThingsResponse {
        return requestHandlers.getValue(request.headers.interactionType)
            .invoke(request)
    }

    private fun handleCommandRequest(request: SmartThingsRequest): SmartThingsResponse {
        val deviceStates = smartThingsService.executeCommands(request.devices!!)
        return SmartThingsResponse(
            createHeaders(request, "commandResponse"),
            deviceState = deviceStates
        )
    }

    private fun handleStateRefreshRequest(request: SmartThingsRequest): SmartThingsResponse {
        val ids = request.devices!!
            .map { it.externalDeviceId }
            .toList()
        val deviceStates = smartThingsService.getDeviceStates(ids)
        return SmartThingsResponse(
            createHeaders(request, "stateRefreshResponse"),
            deviceState = deviceStates
        )
    }

    private fun handleDiscoveryRequest(request: SmartThingsRequest): SmartThingsResponse {
        return SmartThingsResponse(
            createHeaders(request, "discoveryResponse"),
            true,
            smartThingsService.getAllDevices()
        )
    }

    private fun createHeaders(request: SmartThingsRequest, interactionType: String) = Headers(
        interactionType,
        request.headers.requestId
    )
}
