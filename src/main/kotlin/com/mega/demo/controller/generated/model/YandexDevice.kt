package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 * User's device
 * @property name
 * @property description
 * @property room
 * @property type
 * @property capabilities
 * @property deviceInfo
 * @property id
 * @property customData
 */
data class YandexDevice(
    @field:JsonProperty("name") val name: kotlin.String? = null,
    @field:JsonProperty("description") val description: kotlin.String? = null,
    @field:JsonProperty("room") val room: kotlin.String? = null,
    @field:JsonProperty("type") val type: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("capabilities")
    val capabilities: kotlin.collections.List<ShortCapability>? = null,
    @field:Valid @field:JsonProperty("device_info") val deviceInfo: YandexDeviceInfo? = null,
    @field:JsonProperty("id") val id: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("custom_data")
    val customData: kotlin.collections.Map<kotlin.String, kotlin.Any>? = null
)
