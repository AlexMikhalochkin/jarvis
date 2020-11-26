package com.mikhalochkin.jarvis.plc.integration.api;

import java.util.Map;

/**
 * Client for programmable logic controller.
 *
 * @author Alex Mikhalochkin
 */
public interface PlcClient {

    /**
     * Turns on the specified port.
     *
     * @param portNumber port number.
     */
    void turnOn(int portNumber);

    /**
     * Turns off the specified port.
     *
     * @param portNumber port number.
     */
    void turnOff(int portNumber);

    void turnOffAll();

    void turnOnAll();

    /**
     * Returns statuses of all ports.
     *
     * @return ports' statuses.
     */
    Map<Integer, Boolean> getOutPortsStatuses();//TODO: change return type
}
