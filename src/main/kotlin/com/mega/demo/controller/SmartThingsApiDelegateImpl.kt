package com.mega.demo.controller

import com.mega.demo.controller.generated.api.SmartthingsApiDelegate
import com.mega.demo.controller.generated.model.Headers
import com.mega.demo.controller.generated.model.SmartThingsDevice
import com.mega.demo.controller.generated.model.SmartThingsRequest
import com.mega.demo.controller.generated.model.SmartThingsResponse
import com.mega.demo.service.api.SmartThingsService
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
class SmartThingsApiDelegateImpl(val smartThingsService: SmartThingsService, val conversionService: ConversionService) :
    SmartthingsApiDelegate {

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
        val devices = smartThingsService.getAllDevices()
            .mapNotNull { conversionService.convert(it, SmartThingsDevice::class.java) }
        return SmartThingsResponse(
            createHeaders(request, "discoveryResponse"),
            true,
            devices
        )
    }

    private fun handleStateRefreshRequest(request: SmartThingsRequest): SmartThingsResponse {
        val ids = request.devices!!.mapNotNull { it.externalDeviceId }
            .toList()
        val deviceStates = smartThingsService.getDeviceStates(ids)
        return SmartThingsResponse(
            createHeaders(request, "stateRefreshResponse"),
            deviceState = deviceStates
        )
    }

    private fun handleCommandRequest(request: SmartThingsRequest): SmartThingsResponse {
        val deviceStates = smartThingsService.changeState(request.devices!!)
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
