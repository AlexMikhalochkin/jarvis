package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Response with changed states ofdevices
 * @property payload
 * @property requestId
 */
data class ChangeStatesResponse(
    @field:Valid @field:JsonProperty("payload") val payload: ChangeStatesResponsePayload? = null,
    @field:JsonProperty("request_id") val requestId: kotlin.String? = null
)
