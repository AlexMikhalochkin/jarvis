package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Device's capability
 * @property state
 * @property type
 */
data class FullCapability(
    @field:Valid @field:JsonProperty("state", required = true) val state: YandexState,
    @field:JsonProperty("type", required = true) val type: kotlin.String
)
