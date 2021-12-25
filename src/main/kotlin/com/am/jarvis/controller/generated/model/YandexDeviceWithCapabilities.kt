package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * User's device with its capabilities
 * @property id
 * @property capabilities
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class YandexDeviceWithCapabilities(
    @field:JsonProperty("id") val id: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("capabilities")
    val capabilities: kotlin.collections.List<FullCapability>? = null
)
