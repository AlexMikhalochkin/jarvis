package com.mikhalochkin.jarvis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
}
