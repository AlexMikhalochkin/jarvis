package com.am.jarvis.connector.smartthings.converter

import com.am.jarvis.controller.generated.model.CallbackAuthentication
import com.am.jarvis.controller.generated.model.Headers
import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.generateUuid
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * Verifies [GrantCallbackRequestToAccessTokenRequestConverter].
 *
 * @author Alex Mikhalochkin
 */
class GrantCallbackRequestToAccessTokenRequestConverterTest {

    private val clientSecret: String = "test-secret"

    private val converter = GrantCallbackRequestToAccessTokenRequestConverter(clientSecret)

    @Test
    fun testConvert() {
        val callbackAuthentication = CallbackAuthentication(
            grantType = "authorization_code",
            scope = "test",
            code = generateUuid(),
            clientId = generateUuid()
        )
        val source = SmartThingsRequest(
            headers = Headers(interactionType = "grantCallbackRequest", requestId = "test-request-id"),
            callbackAuthentication = callbackAuthentication
        )

        val result = converter.convert(source)

        assertNotNull(result)
        assertNotNull(result.headers.requestId)
        assertEquals("accessTokenRequest", result.headers.interactionType)
        assertEquals(callbackAuthentication.grantType, result.callbackAuthentication?.grantType)
        assertEquals(callbackAuthentication.code, result.callbackAuthentication?.code)
        assertEquals(callbackAuthentication.clientId, result.callbackAuthentication?.clientId)
        assertEquals(clientSecret, result.callbackAuthentication?.clientSecret)
        assertNull(result.callbackAuthentication?.scope)
    }
}
