package com.am.jarvis.integration.api

import com.am.momomo.model.DeviceState
import com.am.momomo.model.Provider

/**
 * Client to perform request to smart home provider.
 *
 * @author Alex Mikhalochkin
 */
interface SmartHomeProviderClient {

    /**
     * Updates [states] in smart home provider.
     */
    fun updateStates(states: List<DeviceState>)

    /**
     * Returns type of smart home provider.
     *
     * @return type of smart home provider.
     */
    fun getProvider(): Provider
}
