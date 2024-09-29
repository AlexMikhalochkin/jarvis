package com.am.jarvis.connector.smartthings.controller.handler

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

/**
 * Test class for [IntegrationDeletedRequestHandler].
 *
 * @author Alex Mikhalochkin
 */
class IntegrationDeletedRequestHandlerTest : BaseRequestHandlerTest<IntegrationDeletedRequestHandler>() {

    @Test
    fun testHandleSmartThingsIntegrationDeleted() {
        val response = executeRequest("integrationDeleted")

        assertSame(HttpStatus.NO_CONTENT, response.statusCode)
    }
}
