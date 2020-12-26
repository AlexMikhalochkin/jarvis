package com.mikhalochkin.jarvis.model.google.status;

/**
 * Represents status of the {@link com.mikhalochkin.jarvis.model.Device} with type action.devices.types.LIGHT
 *
 * @author Alex Mikhalochkin
 */
public class LightStatus extends Status {

    public LightStatus(Boolean on) {
        super(on);
    }
}
