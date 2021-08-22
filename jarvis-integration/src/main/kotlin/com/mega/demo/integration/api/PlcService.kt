package com.mega.demo.service.api

/**
 * Service for programmable logic controller.
 *
 * @author Alex Mikhalochkin
 */
interface PlcService {

    /**
     * Turns on the specified port.
     *
     * @param port port.
     */
    fun turnOn(port: Int)

    /**
     * Turns off the specified port.
     *
     * @param port port.
     */
    fun turnOff(port: Int)

    /**
     * Turns on the specified port.
     *
     * @return [Map] where key is port and value is port's status.
     */
    fun getPortStatuses(): Map<Int, Boolean>
}
