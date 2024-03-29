package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Response with changed states of devices
 * @property requestId
 * @property payload
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ChangeStatesResponse(
    @field:JsonProperty("request_id", required = true) val requestId: kotlin.String,
    @field:Valid @field:JsonProperty("payload") val payload: ChangeStatesResponsePayload? = null
)
