package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Response with all user's devices
 * @property requestId
 * @property payload
 */
data class DevicesResponse2(
    @field:JsonProperty("request_id", required = true) val requestId: kotlin.String,
    @field:Valid @field:JsonProperty("payload") val payload: Payload2? = null
)
