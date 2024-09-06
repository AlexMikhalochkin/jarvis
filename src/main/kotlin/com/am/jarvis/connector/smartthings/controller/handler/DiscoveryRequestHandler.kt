package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsDevice
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import com.am.jarvis.service.api.SmartHomeService
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Component

/**
 * Smart Things request handler for discovery request.
 *
 * @author Alex Mikhalochkin
 */
@Component("discoveryRequest")
class DiscoveryRequestHandler(
    private val smartHomeService: SmartHomeService,
    private val conversionService: ConversionService
) : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): SmartThingsResponse {
        val devices = smartHomeService.getAllDevices()
            .mapNotNull { conversionService.convert(it, SmartThingsDevice::class.java) }
        return SmartThingsResponse(
            createHeaders(request, "discoveryResponse"),
            false,
            devices
        )
    }
}