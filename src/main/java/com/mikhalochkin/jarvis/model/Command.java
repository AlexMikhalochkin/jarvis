package com.mikhalochkin.jarvis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class Command {

    private List<Device> devices;
    private List<Execution> execution;

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Execution> getExecution() {
        return execution;
    }

    public void setExecution(List<Execution> execution) {
        this.execution = execution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Command command = (Command) o;

        return new EqualsBuilder()
                .append(devices, command.devices)
                .append(execution, command.execution)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(devices)
                .append(execution)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("devices", devices)
                .append("execution", execution)
                .toString();
    }
}
