package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * SmartThings device state
 * @property component
 * @property capability
 * @property attribute
 * @property value
 */
data class State(
    @field:JsonProperty("component") val component: kotlin.String? = "main",
    @field:JsonProperty("capability") val capability: kotlin.String? = null,
    @field:JsonProperty("attribute") val attribute: kotlin.String? = null,
    @field:Valid @field:JsonProperty("value") val value: kotlin.Any? = null
)
