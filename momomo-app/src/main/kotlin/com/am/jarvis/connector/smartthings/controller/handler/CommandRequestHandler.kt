package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.DeviceState
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Smart Things request handler for command request.
 *
 * @author Alex Mikhalochkin
 */
@Component("commandRequest")
class CommandRequestHandler(
    private val smartHomeService: SmartHomeService,
    private val conversionService: ConversionService
) : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        val states = request.devices!!
            .mapNotNull { conversionService.convert(it, com.am.jarvis.core.model.DeviceState::class.java) }
        val deviceStates = smartHomeService.changeStates(states, "SMART_THINGS")
            .mapNotNull { conversionService.convert(it, DeviceState::class.java) }
        val smartThingsResponse = SmartThingsResponse(
            createHeaders(request, "commandResponse"),
            deviceState = deviceStates
        )
        return ResponseEntity.ok(smartThingsResponse)
    }
}
