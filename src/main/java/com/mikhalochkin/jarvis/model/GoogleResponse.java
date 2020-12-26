package com.mikhalochkin.jarvis.model;

public class GoogleResponse {
    private Object payload;
    private String requestId;

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Object getPayload() {
        return payload;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }
}
