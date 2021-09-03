package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * SmartThings device state
 * @property component
 * @property capability
 * @property attribute
 * @property value
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class State(
    @field:JsonProperty("component") val component: kotlin.String? = "main",
    @field:JsonProperty("capability") val capability: kotlin.String? = null,
    @field:JsonProperty("attribute") val attribute: kotlin.String? = null,
    @field:Valid @field:JsonProperty("value") val value: kotlin.Any? = null
)
