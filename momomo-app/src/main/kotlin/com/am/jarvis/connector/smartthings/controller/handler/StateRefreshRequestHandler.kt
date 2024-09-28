package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.DeviceState
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Smart Things request handler for state refresh request.
 *
 * @author Alex Mikhalochkin
 */
@Component("stateRefreshRequest")
class StateRefreshRequestHandler(
    private val smartHomeService: SmartHomeService,
    private val conversionService: ConversionService
) : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        val ids = request.devices!!
            .mapNotNull { it.externalDeviceId }
        val deviceStates = smartHomeService.getDeviceStates(ids)
            .mapNotNull { conversionService.convert(it, DeviceState::class.java) }
        val smartThingsResponse = SmartThingsResponse(
            createHeaders(request, "stateRefreshResponse"),
            deviceState = deviceStates
        )
        return ResponseEntity.ok(smartThingsResponse)
    }
}
