package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Request from SmartThings
 * @property headers
 * @property authentication
 * @property devices
 */
data class SmartThingsRequest(
    @field:Valid @field:JsonProperty("headers", required = true) val headers: Headers,
    @field:Valid
    @field:JsonProperty("authentication", required = true)
    val authentication: Authentication,
    @field:Valid
    @field:JsonProperty("devices")
    val devices: kotlin.collections.List<SmartThingsDevice>? = null
)
