package com.am.jarvis.core.model

import org.springframework.web.reactive.function.client.ClientResponse

/**
 * Exception for retryable server errors.
 */
class RetryableServerException(private val clientResponse: ClientResponse) : RuntimeException() {

    override fun toString(): String {
        return super.toString() + clientResponse.toString()
    }
}
