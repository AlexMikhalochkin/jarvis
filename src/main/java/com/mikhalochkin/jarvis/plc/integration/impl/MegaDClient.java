package com.mikhalochkin.jarvis.plc.integration.impl;

import com.google.common.collect.ImmutableSet;
import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of {@link PlcClient} for MegaD.
 *
 * @author Alex Mikhalochkin
 */
@Component
public class MegaDClient implements PlcClient {

    private static final Set<Integer> outPorts = ImmutableSet.of(7, 8, 9, 10, 11, 12, 13, 22, 23, 24, 25, 26, 27, 28);

    @Autowired
    private WebClient webClient;

    @Override
    public void turnOn(int portNumber) {
        validatePort(portNumber);
        changePortStatus(portNumber, 1);
    }

    @Override
    public void turnOff(int portNumber) {
        validatePort(portNumber);
        changePortStatus(portNumber, 0);
    }

    @Override
    public void turnOffAll() {
        changePortStatus(0);
    }

    @Override
    public void turnOnAll() {
        changePortStatus(1);
    }

    @Override
    public Map<Integer, Boolean> getOutPortsStatuses() {
        List<String[]> allStatuses = getAllStatuses();
        return outPorts.stream()
                .collect(Collectors.toMap(Function.identity(), port -> convertPortStatus(allStatuses, port)));
    }

    private boolean convertPortStatus(List<String[]> allStatuses, Integer port) {
        String status = allStatuses.get(port)[0];
        return "ON".equalsIgnoreCase(status);
    }

    private List<String[]> getAllStatuses() {
        return Arrays.stream(getString("all").split(";"))
                .map(status -> status.split("/"))
                .collect(Collectors.toList());
    }

    private void changePortStatus(int portNumber, int portStatus) {
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("pt", "{port}")
                        .queryParam("cmd", "{port}:{status}")
                        .build(portNumber, portNumber, portStatus)
                )
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }

    private void changePortStatus(int portStatus) {
        String response = getString("a:" + portStatus);
    }

    private String getString(String command) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("cmd", command)
                        .build()
                )
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
    }

    private void validatePort(int portNumber) {
        if (!outPorts.contains(portNumber)) {
            throw new RuntimeException("Invalid port number");
        }
    }
}
