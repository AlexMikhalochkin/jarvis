package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * State of Yandex device
 * @property instance
 * @property value
 */
data class YandexState(
    @field:JsonProperty("instance", required = true) val instance: kotlin.String,
    @field:JsonProperty("value", required = true) val value: kotlin.Boolean
)
