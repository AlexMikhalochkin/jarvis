package com.mega.demo.service.api

/**
 * Service for programmable logic controller.
 *
 * @author Alex Mikhalochkin
 */
interface PlcService {

    fun turnOn(port: Int)

    fun turnOff(port: Int)

    fun getPortStatuses(): Map<Int, Boolean>
}
