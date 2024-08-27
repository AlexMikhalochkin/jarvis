package com.am.jarvis.controller

import com.am.jarvis.controller.generated.api.SmartthingsApiDelegate
import com.am.jarvis.controller.generated.model.DeviceState
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.momomo.model.Provider
import com.am.jarvis.service.api.SmartHomeService
import mu.KotlinLogging
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Implementation of generated [SmartthingsApiDelegate].
 *
 * @author Alex Mikhalochkin
 */
@Component
class SmartThingsApiDelegateImpl(
    private val smartHomeService: SmartHomeService,
    private val conversionService: ConversionService
) : SmartthingsApiDelegate {

    private val requestHandlers = mapOf(
        "discoveryRequest" to ::handleDiscoveryRequest,
        "stateRefreshRequest" to ::handleStateRefreshRequest,
        "commandRequest" to ::handleCommandRequest,
        "grantCallbackAccess" to ::handleGrantCallbackAccess,
    )

    override fun handleSmartThings(smartThingsRequest: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        logger.info("Handle SmartThings request. Started. Request ={}", smartThingsRequest)
        val response = requestHandlers.getValue(smartThingsRequest.headers.interactionType).invoke(smartThingsRequest)
        logger.info("Handle SmartThings request. Finished. Response ={}", response)
        return ResponseEntity.ok(response)
    }

    private fun handleDiscoveryRequest(request: SmartThingsRequest): SmartThingsResponse {
        val devices = smartHomeService.getAllDevices()
            .map { conversionService.convert(it, SmartThingsDevice::class.java)!! }
        return SmartThingsResponse(
            createHeaders(request, "discoveryResponse"),
            false,
            devices
        )
    }

    private fun handleStateRefreshRequest(request: SmartThingsRequest): SmartThingsResponse {
        val ids = request.devices!!
            .map { it.externalDeviceId!! }
        val deviceStates = smartHomeService.getDeviceStates(ids)
            .map { conversionService.convert(it, DeviceState::class.java)!! }
        return SmartThingsResponse(
            createHeaders(request, "stateRefreshResponse"),
            deviceState = deviceStates
        )
    }

    private fun handleCommandRequest(request: SmartThingsRequest): SmartThingsResponse {
        val states =
            request.devices!!.map { conversionService.convert(it, com.am.momomo.model.DeviceState::class.java)!! }
        val deviceStates = smartHomeService.changeStates(states, Provider.SMART_THINGS)
            .map { conversionService.convert(it, DeviceState::class.java)!! }
        return SmartThingsResponse(
            createHeaders(request, "commandResponse"),
            deviceState = deviceStates
        )
    }

    private fun handleGrantCallbackAccess(request: SmartThingsRequest): SmartThingsResponse {
        return SmartThingsResponse(
            createHeaders(request, "grantCallbackAccess")
        )
    }

    private fun createHeaders(request: SmartThingsRequest, interactionType: String) =
        Headers(
            interactionType = interactionType,
            requestId = request.headers.requestId
        )
}
