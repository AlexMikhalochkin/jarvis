package com.mikhalochkin.jarvis.model.google.status;

/**
 * Represents status of the {@link com.mikhalochkin.jarvis.model.Device} with type action.devices.types.SENSOR
 *
 * @author Alex Mikhalochkin
 */
public class SensorStatus extends Status {

    private final int temperatureSetpointCelsius;

    public SensorStatus() {
        super(true);
        this.temperatureSetpointCelsius = 150;
    }

    public int getTemperatureSetpointCelsius() {
        return temperatureSetpointCelsius;
    }
}
