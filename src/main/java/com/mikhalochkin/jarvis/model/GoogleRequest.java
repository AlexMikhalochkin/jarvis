package com.mikhalochkin.jarvis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Represents request from google.
 *
 * @author Alex Mikhalochkin
 */
public class GoogleRequest {

    private String requestId;
    private List<Input> inputs;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GoogleRequest that = (GoogleRequest) o;

        return new EqualsBuilder()
                .append(requestId, that.requestId)
                .append(inputs, that.inputs)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(requestId)
                .append(inputs)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("requestId", requestId)
                .append("inputs", inputs)
                .toString();
    }
}
