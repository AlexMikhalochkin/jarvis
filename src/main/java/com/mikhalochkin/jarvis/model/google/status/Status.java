package com.mikhalochkin.jarvis.model.google.status;

/**
 * Represents status of the Google device
 *
 * @author Alex Mikhalochkin
 */
public abstract class Status {
    protected final boolean on;
    protected final String status;
    protected final boolean online;

    public Status(Boolean on) {
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
