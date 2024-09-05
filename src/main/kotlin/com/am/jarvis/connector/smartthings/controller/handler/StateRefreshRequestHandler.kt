package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.DeviceState
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.core.convert.ConversionService
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

    override fun invoke(request: SmartThingsRequest): SmartThingsResponse {
        val ids = request.devices!!
            .map { it.externalDeviceId!! }
            .toList()
        val deviceStates = smartHomeService.getDeviceStates(ids)
            .map { conversionService.convert(it, DeviceState::class.java)!! }
        return SmartThingsResponse(
            createHeaders(request, "stateRefreshResponse"),
            deviceState = deviceStates
        )
    }
}
