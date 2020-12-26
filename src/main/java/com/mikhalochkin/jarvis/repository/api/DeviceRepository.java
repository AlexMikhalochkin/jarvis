package com.mikhalochkin.jarvis.repository.api;

import com.mikhalochkin.jarvis.model.Device;

import java.util.List;

public interface DeviceRepository {
    List<Device> getAll();

    Device getById(String id);
}
