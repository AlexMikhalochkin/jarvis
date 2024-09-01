package com.am.momomo.connector.megad.client.mqtt

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

/**
 * Deserializer for MegaD port state.
 *
 * @author Alex Mikhalochkin
 */
class MegaDPortStateDeserializer: JsonDeserializer<Boolean>() {

    override fun deserialize(jsonParser: JsonParser?, ctxt: DeserializationContext?): Boolean {
        return jsonParser != null && "ON" == jsonParser.text
    }
}