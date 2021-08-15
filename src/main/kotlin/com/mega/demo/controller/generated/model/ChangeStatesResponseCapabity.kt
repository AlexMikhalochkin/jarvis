package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * Device's capability
 * @property type
 * @property state
 */
data class ChangeStatesResponseCapabity(
    @field:JsonProperty("type") val type: kotlin.String? = null,
    @field:Valid @field:JsonProperty("state") val state: ChangeStatesResponseState? = null
)
