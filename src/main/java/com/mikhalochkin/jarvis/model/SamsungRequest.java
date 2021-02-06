package com.mikhalochkin.jarvis.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * TODO: add description
 *
 * @author Alex Mikhalochkin
 */
public class SamsungRequest {

    private String lifecycle;
    private String executionId;
    private String appId;
    private String locale;
    private String version;
    private Map<String, Object> confirmationData;
    private Map<String, Object> configurationData;
    private Map<String, Object> settings;

    public String getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getConfirmationData() {
        return confirmationData;
    }

    public void setConfirmationData(Map<String, Object> confirmationData) {
        this.confirmationData = confirmationData;
    }

    public Map<String, Object> getConfigurationData() {
        return configurationData;
    }

    public void setConfigurationData(Map<String, Object> configurationData) {
        this.configurationData = configurationData;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("lifecycle", lifecycle)
                .append("executionId", executionId)
                .append("appId", appId)
                .append("locale", locale)
                .append("version", version)
                .append("confirmationData", confirmationData)
                .append("configurationData", configurationData)
                .append("settings", settings)
                .toString();
    }
}
