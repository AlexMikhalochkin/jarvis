package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * s
 * @property headers
 * @property authentication
 */
data class SmartThingsRequest(
    @field:Valid @field:JsonProperty("headers", required = true) val headers: Headers,
    @field:Valid
    @field:JsonProperty("authentication", required = true)
    val authentication: Authentication
)
