package com.mega.demo.integration.api

/**
 * Http client for programmable logic controller.
 *
 * @author Alex Mikhalochkin
 */
interface PlcClient {

    fun turnOn(port: Int)

    fun turnOff(port: Int)

    fun getPortStatuses(): Map<Int, Boolean>
}
