package com.mega.demo.controller.generated.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.annotation.Generated

/**
 * State of Yandex device
 * @property instance
 * @property value
 */
@Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
data class YandexState(
    @field:JsonProperty("instance", required = true) val instance: kotlin.String,
    @field:JsonProperty("value", required = true) val value: kotlin.Boolean
)
