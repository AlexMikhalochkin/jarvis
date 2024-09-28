package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.connector.smartthings.notifier.SmartThingsTokenService
import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Smart Things request handler for discovery request.
 *
 * @author Alex Mikhalochkin
 */
@Component("discoveryRequest")
class DiscoveryRequestHandler(
    private val smartHomeService: SmartHomeService,
    private val conversionService: ConversionService,
    private val tokenService: SmartThingsTokenService
) : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        val devices = smartHomeService.getAllDevices()
            .mapNotNull { conversionService.convert(it, SmartThingsDevice::class.java) }
        val smartThingsResponse = SmartThingsResponse(
            createHeaders(request, "discoveryResponse"),
            tokenService.isGrantCallbackAccessRequired(),
            devices
        )
        return ResponseEntity.ok(smartThingsResponse)
    }
}
