package com.mikhalochkin.jarvis.model.google.status;

/**
 * Represents status of the {@link com.mikhalochkin.jarvis.model.Device} with type action.devices.types.LIGHT
 *
 * @author Alex Mikhalochkin
 */
public class LightStatus extends Status {

    private final boolean on;
    private final String status;
    private final boolean online;

    public LightStatus(Boolean on) {
        this.on = on;
        this.status = "SUCCESS";
        this.online = true;
    }

    public boolean isOn() {
        return on;
    }

    public String getStatus() {
        return status;
    }

    public boolean isOnline() {
        return online;
    }
}
