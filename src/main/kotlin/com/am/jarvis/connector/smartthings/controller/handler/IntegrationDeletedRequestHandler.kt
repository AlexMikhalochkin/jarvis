package com.am.jarvis.connector.smartthings.controller.handler

import com.am.jarvis.controller.generated.model.SmartThingsRequest
import com.am.jarvis.controller.generated.model.SmartThingsResponse
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

/**
 * Smart Things request handler for integration deleted request.
 *
 * @author Alex Mikhalochkin`
 */
@Component("integrationDeleted")
class IntegrationDeletedRequestHandler : BaseRequestHandler() {

    override fun invoke(request: SmartThingsRequest): ResponseEntity<SmartThingsResponse> {
        logger.info("SmartThings integration deleted. RequestId: ${request.headers.requestId}")
        return ResponseEntity.noContent().build()
    }
}
