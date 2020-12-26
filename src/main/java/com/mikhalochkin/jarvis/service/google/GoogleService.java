package com.mikhalochkin.jarvis.service.google;

import com.google.common.collect.ImmutableMap;
import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.model.google.status.LightStatus;
import com.mikhalochkin.jarvis.model.google.status.SensorStatus;
import com.mikhalochkin.jarvis.model.google.status.Status;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import com.mikhalochkin.jarvis.repository.api.DeviceRepository;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link SmartHomeService} for Google.
 *
 * @author Alex Mikhalochkin
 */
@Service
public class GoogleService implements SmartHomeService {

    @Autowired
    private PlcClient plcClient;
    @Autowired
    private DeviceRepository deviceRepository;

    private final Map<String, Function<Boolean, ? extends Status>> statusFunctions = ImmutableMap.of(
            "action.devices.types.LIGHT", this::createLightStatus,
            "action.devices.types.SENSOR", this::createSensorStatus
    );

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.getAll();
    }

    @Override
    public Map<String, ? extends Status> getStatuses(List<String> deviceIds) {
        Map<Integer, Boolean> outPortsStatuses = plcClient.getOutPortsStatuses();
        return deviceIds.stream()
                .map(deviceRepository::getById)
                .collect(Collectors.toMap(Device::getId,
                        device -> statusFunctions.get(device.getType()).apply(outPortsStatuses.get(device.getPort()))));
    }

    @Override
    public void changeStatus(List<String> deviceIds, Boolean isOn) {
        Consumer<Integer> idsConsumer = isOn
                ? plcClient::turnOn
                : plcClient::turnOff;
        deviceIds.stream()
                .map(deviceRepository::getById)
                .map(Device::getPort)
                .forEach(idsConsumer);
    }

    private Status createLightStatus(boolean on) {
        return new LightStatus(on);
    }

    private Status createSensorStatus(boolean on) {
        return new SensorStatus();
    }
}
