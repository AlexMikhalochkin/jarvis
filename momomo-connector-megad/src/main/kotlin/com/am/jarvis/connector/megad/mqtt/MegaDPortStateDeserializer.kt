package com.am.jarvis.connector.megad.mqtt

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

/**
 * Deserializer for MegaD port state.
 *
 * @author Alex Mikhalochkin
 */
class MegaDPortStateDeserializer : JsonDeserializer<Boolean>() {

    override fun deserialize(jsonParser: JsonParser?, ctxt: DeserializationContext?): Boolean {
        return "ON" == jsonParser?.text
    }
}
