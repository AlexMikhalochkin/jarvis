package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import org.springframework.stereotype.Component

/**
 * Smart Things request handler for grant callback request.
 *
 * @author Alex Mikhalochkin
 */
@Component("grantCallbackAccess")
class GrantCallbackAccessRequestHandler : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): SmartThingsResponse {
        return SmartThingsResponse(
            createHeaders(request, "grantCallbackAccess")
        )
    }
}
