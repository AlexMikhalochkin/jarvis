package com.mikhalochkin.jarvis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Represents name for {@link Device}.
 *
 * @author Alex Mikhalochkin
 */
public class DeviceName {

    private List<String> defaultNames;
    private String name;
    private List<String> nicknames;

    public DeviceName() {
    }

    public DeviceName(List<String> defaultNames, String name, List<String> nicknames) {
        this.defaultNames = defaultNames;
        this.name = name;
        this.nicknames = nicknames;
    }

    public List<String> getDefaultNames() {
        return defaultNames;
    }

    public void setDefaultNames(List<String> defaultNames) {
        this.defaultNames = defaultNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DeviceName that = (DeviceName) o;

        return new EqualsBuilder()
                .append(defaultNames, that.defaultNames)
                .append(name, that.name)
                .append(nicknames, that.nicknames)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(defaultNames)
                .append(name)
                .append(nicknames)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("defaultNames", defaultNames)
                .append("name", name)
                .append("nicknames", nicknames)
                .toString();
    }
}
