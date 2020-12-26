package com.mikhalochkin.jarvis.service.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.mikhalochkin.jarvis.repository.api.DeviceRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mikhalochkin.jarvis.model.Device;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import com.mikhalochkin.jarvis.service.api.SmartHomeService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link SmartHomeService} for Google.
 *
 * @author Alex Mikhalochkin
 */
@Service
public class GoogleService implements SmartHomeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleService.class);

    @Autowired
    private PlcClient plcClient;
    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.getAll();
    }

    @Override
    public Map<String, Object> getStatuses(List<String> deviceIds) {
        Map<Integer, Boolean> outPortsStatuses = plcClient.getOutPortsStatuses();
        return deviceIds.stream()
                .map(deviceRepository::getById)
                .map(device -> Pair.of(device.getId(), outPortsStatuses.get(device.getPort())))
                .map(pair -> Pair.of(pair.getLeft(), ImmutableMap.of("on", pair.getRight(), "online", true, "status", "SUCCESS")))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
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
}
