package com.am.jarvis.connector.smartthings.controller.handler

import org.junit.jupiter.api.Test

class GrantCallbackAccessRequestHandlerTest : BaseRequestHandlerTest<GrantCallbackAccessRequestHandler>() {

    @Test
    fun testHandleSmartThingsGrantCallbackAccess() {
        val body = executeRequest("grantCallbackAccess")
        verifyHeaders(body, "grantCallbackAccess")
    }
}
