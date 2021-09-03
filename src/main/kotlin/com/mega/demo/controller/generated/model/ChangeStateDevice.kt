package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Device's capability
 * @property id
 * @property capabilities
 * @property customData
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class ChangeStateDevice(
    @field:JsonProperty("id", required = true) val id: kotlin.String,
    @field:Valid
    @field:JsonProperty("capabilities", required = true)
    val capabilities: kotlin.collections.List<FullCapability>,
    @field:Valid
    @field:JsonProperty("custom_data")
    val customData: kotlin.collections.Map<kotlin.String, kotlin.Any>? = null
)
