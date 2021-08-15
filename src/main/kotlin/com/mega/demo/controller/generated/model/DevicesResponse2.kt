package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Response with all user's devices
 * @property payload
 * @property requestId
 */
data class DevicesResponse2(
    @field:Valid @field:JsonProperty("payload") val payload: Payload2? = null,
    @field:JsonProperty("request_id") val requestId: kotlin.String? = null
)
