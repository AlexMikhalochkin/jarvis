package com.am.jarvis.connector.smartthings.notifier

import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.UUID

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
                requestId = UUID.randomUUID().toString()
            ),
            callbackAuthentication = callbackAuthenticationWithClientSecret
        )
    }
}
