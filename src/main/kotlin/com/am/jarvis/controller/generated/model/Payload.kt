package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * Object with devices
 * @property userId
 * @property devices
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class Payload(
    @field:JsonProperty("user_id", required = true) val userId: kotlin.String,
    @field:Valid
    @field:JsonProperty("devices", required = true)
    val devices: kotlin.collections.List<YandexDevice>
)
