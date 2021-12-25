package com.am.jarvis.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated
import javax.validation.Valid

/**
 * User's device
 * @property name
 * @property type
 * @property capabilities
 * @property deviceInfo
 * @property id
 * @property description
 * @property room
 * @property customData
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class YandexDevice(
    @field:JsonProperty("name", required = true) val name: kotlin.String,
    @field:JsonProperty("type", required = true) val type: kotlin.String,
    @field:Valid
    @field:JsonProperty("capabilities", required = true)
    val capabilities: kotlin.collections.List<ShortCapability>,
    @field:Valid
    @field:JsonProperty("device_info", required = true)
    val deviceInfo: YandexDeviceInfo,
    @field:JsonProperty("id", required = true) val id: kotlin.String,
    @field:JsonProperty("description") val description: kotlin.String? = null,
    @field:JsonProperty("room") val room: kotlin.String? = null,
    @field:Valid
    @field:JsonProperty("custom_data")
    val customData: kotlin.collections.Map<kotlin.String, kotlin.Any>? = null
)
