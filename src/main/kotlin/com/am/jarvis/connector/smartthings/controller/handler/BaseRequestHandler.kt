package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse

/**
 * Base class for all request handlers
 *
 * @author Alex Mikhalochkin
 */
abstract class BaseRequestHandler : (SmartThingsRequest) -> SmartThingsResponse {

    protected fun createHeaders(request: SmartThingsRequest, interactionType: String) =
        Headers(
            interactionType = interactionType,
            requestId = request.headers.requestId
        )
}
