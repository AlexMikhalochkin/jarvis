package com.am.jarvis.connector.megad.mqtt

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

/**
 * Represents the state of a MegaD port.
 *
 * @author Alex Mikhalochkin
 */
data class MegaDPortState(
    val port: Int,
    @JsonProperty("value")
    @JsonDeserialize(using = MegaDPortStateDeserializer::class)
    val isOn: Boolean
)
