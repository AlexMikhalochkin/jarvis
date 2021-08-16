package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Response with changed states ofdevices
 * @property requestId
 * @property payload
 */
data class ChangeStatesResponse(
    @field:JsonProperty("request_id", required = true) val requestId: kotlin.String,
    @field:Valid @field:JsonProperty("payload") val payload: ChangeStatesResponsePayload? = null
)