package live.databo3.sensor.ruleEngine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.rule_engine.controller.RuleEngineController;
import live.databo3.sensor.rule_engine.dto.RegisterSensorFromRuleEngineRequest;
import live.databo3.sensor.rule_engine.service.RuleEngineService;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RuleEngineController.class)
class RuleEngineControllerTest {

    @MockBean
    private RuleEngineService ruleEngineService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void registerSensorTest() throws Exception {
        mockMvc.perform(post("/sensor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        RegisterSensorFromRuleEngineRequest.builder()
                                .branch("testOrganization")
                                .place("testPlace")
                                .device("testSensor")
                                .endpoint("testSensorType")
                                .build())))
                .andExpect(status().isOk());
    }
}
