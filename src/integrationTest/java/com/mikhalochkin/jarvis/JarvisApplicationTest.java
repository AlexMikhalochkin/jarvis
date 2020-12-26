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
import org.springframework.test.web.servlet.ResultActions;

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
        String request = testService.getPayloadFromFile("sync_request.json");
        String expectedResponse = testService.getPayloadFromFile("sync_response.json");
        ResultActions resultActions = mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void testQueryRequest() throws Exception {
        String request = testService.getPayloadFromFile("query_request.json");
        String expectedResponse = testService.getPayloadFromFile("query_response.json");
        Mockito.when(plcClient.getOutPortsStatuses()).thenReturn(Collections.singletonMap(7, true));
        ResultActions resultActions = mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
        Mockito.verify(plcClient).getOutPortsStatuses();
    }
}
