package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.connector.smartthings.notifier.SmartThingsTokenService
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Smart Things request handler for grant callback request.
 *
 * @author Alex Mikhalochkin
 */
@Component("grantCallbackAccess")
class GrantCallbackAccessRequestHandler(
    private val tokenService: SmartThingsTokenService
) : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        tokenService.storeCallbackToken(request)
        val smartThingsResponse = SmartThingsResponse(
            createHeaders(request, "grantCallbackAccess")
        )
        return ResponseEntity.ok(smartThingsResponse)
    }
}
