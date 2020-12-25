package com.mikhalochkin.jarvis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Represents payload of the {@link GoogleRequest}.
 *
 * @author Alex Mikhalochkin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    private String agentUserId;
    private List<Device> devices;
    private List<Command> commands;

    public String getAgentUserId() {
        return agentUserId;
    }

    public void setAgentUserId(String agentUserId) {
        this.agentUserId = agentUserId;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Payload payload = (Payload) o;

        return new EqualsBuilder()
                .append(agentUserId, payload.agentUserId)
                .append(devices, payload.devices)
                .append(commands, payload.commands)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(agentUserId)
                .append(devices)
                .append(commands)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("agentUserId", agentUserId)
                .append("devices", devices)
                .append("commands", commands)
                .toString();
    }
}
