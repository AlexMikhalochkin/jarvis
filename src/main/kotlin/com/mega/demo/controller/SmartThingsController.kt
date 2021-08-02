package com.mega.demo.controller

import com.mega.demo.controller.model.smartthings.DeviceState
import com.mega.demo.controller.model.smartthings.Headers
import com.mega.demo.controller.model.smartthings.SmartThingsDevice
import com.mega.demo.controller.model.smartthings.SmartThingsRequest
import com.mega.demo.controller.model.smartthings.SmartThingsResponse
import com.mega.demo.controller.model.smartthings.State
import com.mega.demo.service.api.SmartHomeService
import org.slf4j.LoggerFactory
import org.springframework.core.convert.ConversionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * SmartThings controller.
 *
 * @author Alex Mikhalochkin
 */
@RestController
class SmartThingsController(val smartThingsService: SmartHomeService, val conversionService: ConversionService) {

    private val requestHandlers = mapOf(
        "discoveryRequest" to ::handleDiscoveryRequest,
        "stateRefreshRequest" to ::handleStateRefreshRequest,
        "commandRequest" to ::handleCommandRequest
    )

    @PostMapping(path = ["/smartthings"])
    fun handle(@RequestBody request: SmartThingsRequest): SmartThingsResponse {
        logger.info("Process request from samsung. Started. Request={}", request)
        return requestHandlers.getValue(request.headers.interactionType)
            .invoke(request)
    }

    private fun handleCommandRequest(request: SmartThingsRequest): SmartThingsResponse {
        val devices = request.devices
        val device = devices!![0]
        val commands = device.commands
        val command = commands!![0]
        val isOn = "on" == command.command
        val externalDeviceId = device.externalDeviceId
        smartThingsService.changeStatus(listOf(externalDeviceId), isOn)
        val state = State("main", "st.switch", "switch", isOn)
        val deviceState = DeviceState(externalDeviceId, listOf(state))
        return SmartThingsResponse(
            createHeaders(request, "commandResponse"),
            deviceState = listOf(deviceState)
        )
    }

    private fun handleStateRefreshRequest(request: SmartThingsRequest): SmartThingsResponse {
        val ids = request.devices!!
            .map { it.externalDeviceId }
            .toList()
        val deviceStates = smartThingsService.getStatuses(ids)
            .map { conversionService.convert(it, DeviceState::class.java) }
            .toList()
        return SmartThingsResponse(
            createHeaders(request, "stateRefreshResponse"),
            deviceState = deviceStates
        )
    }

    private fun handleDiscoveryRequest(request: SmartThingsRequest): SmartThingsResponse {
        val dev = smartThingsService.getAllDevices()
            .map { conversionService.convert(it, SmartThingsDevice::class.java) }
        return SmartThingsResponse(
            createHeaders(request, "discoveryResponse"),
            true,
            dev
        )
    }

    private fun createHeaders(request: SmartThingsRequest, interactionType: String) = Headers(
        interactionType,
        request.headers.requestId
    )

    companion object {
        private val logger = LoggerFactory.getLogger(SmartThingsController::class.java)
    }
}
