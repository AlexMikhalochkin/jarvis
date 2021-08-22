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
    @field:JsonProperty("manufacturer", required = true) val manufacturer: kotlin.String,
    @field:JsonProperty("model", required = true) val model: kotlin.String,
    @field:JsonProperty("hw_version") val hwVersion: kotlin.String? = null,
    @field:JsonProperty("sw_version") val swVersion: kotlin.String? = null
)
