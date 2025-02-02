package com.am.jarvis.connector.smartthings.notifier

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Tests for [RefreshAccessTokensRequestConverter].
 *
 * @author Alex Mikhalochkin
 */
class RefreshAccessTokensRequestConverterTest {

    private val clientSecret = "testSecret"
    private val clientId = "testId"
    private val converter = RefreshAccessTokensRequestConverter(clientSecret, clientId)

    @Test
    fun convertWithValidRefreshToken() {
        val refreshToken = "validRefreshToken"
        val result = converter.convert(refreshToken)

        val callbackAuthentication = result.callbackAuthentication!!
        assertEquals("refresh_token", callbackAuthentication.grantType)
        assertEquals(refreshToken, callbackAuthentication.refreshToken)
        assertEquals(clientId, callbackAuthentication.clientId)
        assertEquals(clientSecret, callbackAuthentication.clientSecret)
        assertEquals("refreshAccessTokens", result.headers.interactionType)
    }
}
