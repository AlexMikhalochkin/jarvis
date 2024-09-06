package com.am.jarvis.connector.smartthings.controller

import com.am.jarvis.controller.generated.api.SmartthingsApiDelegate
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Implementation of generated [SmartthingsApiDelegate].
 *
 * @author Alex Mikhalochkin
 */
@Component
class SmartThingsApiDelegateImpl(
    private val requestHandlers: Map<String, (SmartThingsRequest) -> ResponseEntity<SmartThingsResponse>>
) : SmartthingsApiDelegate {

    override fun handleSmartThings(smartThingsRequest: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        val interactionType = smartThingsRequest.headers.interactionType
        return requestHandlers.getValue(interactionType)
            .invoke(smartThingsRequest)
    }
}
