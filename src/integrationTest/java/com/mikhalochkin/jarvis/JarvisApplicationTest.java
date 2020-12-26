package com.mikhalochkin.jarvis;

import com.mikhalochkin.jarvis.plc.integration.api.PlcClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JarvisApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class JarvisApplicationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TestService testService;
    @MockBean
    private PlcClient plcClient;

    @Test
    public void testSyncRequest() throws Exception {
        verifyResponse("sync_request.json", "sync_response.json", false);
    }

    @Test
    public void testQueryRequest() throws Exception {
        Mockito.when(plcClient.getOutPortsStatuses()).thenReturn(Collections.singletonMap(7, true));
        verifyResponse("query_request.json", "query_response.json", true);
        Mockito.verify(plcClient).getOutPortsStatuses();
    }

    @Test
    public void testExecuteRequest() throws Exception {
        Mockito.doNothing().when(plcClient).turnOn(7);
        verifyResponse("execute_request.json", "execute_response.json", true);
        Mockito.verify(plcClient).turnOn(7);
    }

    @Test
    public void testQueryRequestForSensor() throws Exception {
        Mockito.when(plcClient.getOutPortsStatuses()).thenReturn(Collections.singletonMap(10, true));
        verifyResponse("query_request_sensor.json", "query_response_sensor.json", true);
        Mockito.verify(plcClient).getOutPortsStatuses();
    }

    private void verifyResponse(String requestPath, String responsePath, boolean isStrict) throws Exception {
        String request = testService.getPayloadFromFile(requestPath);
        String expectedResponse = testService.getPayloadFromFile(responsePath);
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, isStrict));
    }
}
