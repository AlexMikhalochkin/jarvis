package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.CallbackAuthentication
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
class RefreshAccessTokensRequestConverter(
    @Value("\${smart-things.client.secret}") private val clientSecret: String,
    @Value("\${smart-things.client.id}") private val clientId: String
) : Converter<String, SmartThingsRequest> {

    override fun convert(refreshToken: String): SmartThingsRequest {
        val callbackAuthentication = CallbackAuthentication(
            grantType = "refresh_token",
            refreshToken = refreshToken,
            clientId = clientId,
            clientSecret = clientSecret
        )
        val headers = Headers(
            interactionType = "refreshAccessTokens",
            requestId = UUID.randomUUID().toString()
        )
        return SmartThingsRequest(
            headers = headers,
            callbackAuthentication = callbackAuthentication
        )
    }
}
