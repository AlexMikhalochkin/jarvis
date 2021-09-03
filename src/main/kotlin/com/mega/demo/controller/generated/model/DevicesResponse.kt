package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Response with all user's devices
 * @property payload
 * @property requestId
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class DevicesResponse(
    @field:Valid @field:JsonProperty("payload", required = true) val payload: Payload,
    @field:JsonProperty("request_id", required = true) val requestId: kotlin.String
)
