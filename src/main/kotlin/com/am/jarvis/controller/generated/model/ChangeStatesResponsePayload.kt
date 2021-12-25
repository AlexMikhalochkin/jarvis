package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Object with devices
 * @property devices
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ChangeStatesResponsePayload(
    @field:Valid
    @field:JsonProperty("devices")
    val devices: kotlin.collections.List<ChangeStatesResponseDevice>? = null
)
