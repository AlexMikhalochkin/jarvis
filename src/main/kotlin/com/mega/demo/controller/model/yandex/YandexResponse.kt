package com.mega.demo.controller.model.yandex

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents Yandex response.
 *
 * @author Alex Mikhalochkin
 */
data class YandexResponse(
    @JsonProperty("request_id") val requestId: String,
    val payload: Payload
)
