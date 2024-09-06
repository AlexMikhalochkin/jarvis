package com.am.jarvis.connector.smartthings.controller.handler

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

/**
 * Test class for [GrantCallbackAccessRequestHandler].
 *
 * @author Alex Mikhalochkin
 */
class GrantCallbackAccessRequestHandlerTest : BaseRequestHandlerTest<GrantCallbackAccessRequestHandler>() {

    @Test
    fun testHandleSmartThingsGrantCallbackAccess() {
        val response = executeRequest("grantCallbackAccess")

        verifyHeaders(response.body, "grantCallbackAccess")
        assertSame(HttpStatus.OK, response.statusCode)
    }
}
