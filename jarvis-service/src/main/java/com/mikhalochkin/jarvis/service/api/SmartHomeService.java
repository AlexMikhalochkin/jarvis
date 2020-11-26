package com.mikhalochkin.jarvis.service.api;

import com.mikhalochkin.jarvis.model.Device;

import java.util.List;
import java.util.Map;

/**
 * Service for smart home.
 *
 * @author Alex Mikhalochkin
 */
public interface SmartHomeService {

    /**
     * Returns all available devices.
     *
     * @return {@link List} of {@link Device}
     */
    List<Device> getAllDevices();

    /**
     * Returns statuses for the specified devices.
     *
     * @param deviceIds {@link List} of {@link Device} ids.
     * @return {@link Map} where key is id of the {@link Device} and values is state of the {@link Device}.
     */
    Map<String, Object> getStatuses(List<String> deviceIds);

    /**
     * Changes statuses for the specified devices.
     *
     * @param deviceIds {@link List} of {@link Device} ids.
     * @param isOn      new status for the {@link Device}.
     */
    void changeStatus(List<String> deviceIds, Boolean isOn);
}
