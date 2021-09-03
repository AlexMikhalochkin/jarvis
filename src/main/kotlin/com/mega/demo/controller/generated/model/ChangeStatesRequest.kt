package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Request to change devices' states
 * @property payload
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ChangeStatesRequest(
    @field:Valid
    @field:JsonProperty("payload", required = true)
    val payload: ChangeStatesRequestPayload
)
