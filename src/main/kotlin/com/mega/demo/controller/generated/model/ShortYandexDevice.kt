package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * User's device
 * @property id
 * @property customData
 */
data class ShortYandexDevice(
    @field:JsonProperty("id") val id: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("custom_data")
    val customData: kotlin.collections.Map<kotlin.String, kotlin.Any>? = null
)