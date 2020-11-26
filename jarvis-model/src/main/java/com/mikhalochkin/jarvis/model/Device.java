package com.mikhalochkin.jarvis.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * Represents device for google home.
 *
 * @author Alex Mikhalochkin
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {

    private String id;
    private String type;
    private List<String> traits;
    private DeviceName name;
    private boolean willReportState;
    private String roomHint;
    private Map<String, Object> attributes;
    private Map<String, String> deviceInfo;
    private List<Object> otherDeviceIds;
    private Map<String, Object> customData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    public DeviceName getName() {
        return name;
    }

    public void setName(DeviceName name) {
        this.name = name;
    }

    public boolean isWillReportState() {
        return willReportState;
    }

    public void setWillReportState(boolean willReportState) {
        this.willReportState = willReportState;
    }

    public String getRoomHint() {
        return roomHint;
    }

    public void setRoomHint(String roomHint) {
        this.roomHint = roomHint;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Map<String, String> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public List<Object> getOtherDeviceIds() {
        return otherDeviceIds;
    }

    public void setOtherDeviceIds(List<Object> otherDeviceIds) {
        this.otherDeviceIds = otherDeviceIds;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return new EqualsBuilder()
                .append(willReportState, device.willReportState)
                .append(id, device.id)
                .append(type, device.type)
                .append(traits, device.traits)
                .append(name, device.name)
                .append(roomHint, device.roomHint)
                .append(attributes, device.attributes)
                .append(deviceInfo, device.deviceInfo)
                .append(otherDeviceIds, device.otherDeviceIds)
                .append(customData, device.customData)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(type)
                .append(traits)
                .append(name)
                .append(willReportState)
                .append(roomHint)
                .append(attributes)
                .append(deviceInfo)
                .append(otherDeviceIds)
                .append(customData)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("type", type)
                .append("traits", traits)
                .append("name", name)
                .append("willReportState", willReportState)
                .append("roomHint", roomHint)
                .append("attributes", attributes)
                .append("deviceInfo", deviceInfo)
                .append("otherDeviceIds", otherDeviceIds)
                .append("customData", customData)
                .toString();
    }
}
