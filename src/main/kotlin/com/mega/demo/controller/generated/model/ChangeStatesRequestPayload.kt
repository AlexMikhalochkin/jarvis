package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Object with devices
 * @property devices
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ChangeStatesRequestPayload(
    @field:Valid
    @field:JsonProperty("devices", required = true)
    val devices: kotlin.collections.List<ChangeStateDevice>
)
