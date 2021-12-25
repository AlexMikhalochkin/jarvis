package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Device's capability
 * @property state
 * @property type
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class FullCapability(
    @field:Valid @field:JsonProperty("state", required = true) val state: YandexState,
    @field:JsonProperty("type", required = true) val type: kotlin.String
)
