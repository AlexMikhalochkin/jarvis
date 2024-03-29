package com.am.jarvis.integration.api

import com.am.jarvis.controller.generated.model.DeviceState

/**
 * Client to perform request to SmartThings.
 *
 * @author Alex Mikhalochkin
 */
interface SmartThingsCallbackClient {

    /**
     * Gets token for requests to SmartThings.
     *
     * @return callback authentication token.
     */
    fun getToken(authenticationCode: String): String

    /**
     * Pushes devises' states to SmartThings.
     *
     * @param deviceStates [List] of [DeviceState].
     */
    fun pushStates(deviceStates: List<DeviceState>)
}
