package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Additional technical information about the device
 * @property manufacturer
 * @property model
 * @property hwVersion
 * @property swVersion
 */
data class YandexDeviceInfo(
    @field:JsonProperty("manufacturer") val manufacturer: kotlin.String? = null,
    @field:JsonProperty("model") val model: kotlin.String? = null,
    @field:JsonProperty("hw_version") val hwVersion: kotlin.String? = null,
    @field:JsonProperty("sw_version") val swVersion: kotlin.String? = null
)
