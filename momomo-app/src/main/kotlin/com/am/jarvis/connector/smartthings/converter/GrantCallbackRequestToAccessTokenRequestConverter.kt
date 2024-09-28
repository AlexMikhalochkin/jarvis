package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.generateUuid
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * Converts grant callback [SmartThingsRequest] to callback [SmartThingsRequest].
 *
 * @author Alex Mikhalochkin
 */
@Component
class GrantCallbackRequestToAccessTokenRequestConverter(
    @Value("\${smart-things.client.secret}") private val clientSecret: String
) : Converter<SmartThingsRequest, SmartThingsRequest> {

    override fun convert(source: SmartThingsRequest): SmartThingsRequest {
        val callbackAuthentication = source.callbackAuthentication!!
        val callbackAuthenticationWithClientSecret = callbackAuthentication.copy(
            scope = null,
            clientSecret = clientSecret
        )
        return SmartThingsRequest(
            Headers(
                interactionType = "accessTokenRequest",
                requestId = generateUuid()
            ),
            callbackAuthentication = callbackAuthenticationWithClientSecret
        )
    }
}
