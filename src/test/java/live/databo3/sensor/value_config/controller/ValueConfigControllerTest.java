package live.databo3.sensor.value_config.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigRequest;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
import live.databo3.sensor.value_config.service.ValueConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ValueConfigController.class)
class ValueConfigControllerTest {

    @MockBean
    private ValueConfigService valueConfigService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createValueConfigTest() throws Exception {
        ValueConfigResponse valueConfigResponse = new ValueConfigResponse(1L, 1L, "firstEntry", "secondEntry");
        given(valueConfigService.createValueConfig(anyInt(), anyString(), anyInt(), any(ValueConfigRequest.class))).willReturn(valueConfigResponse);

        mockMvc.perform(post("/api/sensor/org/1/sensor/testSensor/sensorType/1/value")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ValueConfigRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(valueConfigResponse)));
    }

    @Test
    void modifyValueConfigTest() throws Exception {
        ValueConfigResponse valueConfigResponse = new ValueConfigResponse(1L, 1L, "firstEntry", "secondEntry");
        given(valueConfigService.modifyValueConfig(anyInt(), anyString(), anyInt(), anyLong(), any(ValueConfigRequest.class))).willReturn(valueConfigResponse);

        mockMvc.perform(put("/api/sensor/org/1/sensor/testSensor/sensorType/1/value/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ValueConfigRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(valueConfigResponse)));
    }

    @Test
    void getValueConfigTest() throws Exception {
        ValueConfigResponse valueConfigResponse = new ValueConfigResponse(1L, 1L, "firstEntry", "secondEntry");
        given(valueConfigService.getValueConfig(anyInt(), anyString(), anyInt(), anyLong())).willReturn(valueConfigResponse);

        mockMvc.perform(get("/api/sensor/org/1/sensor/testSensor/sensorType/1/value/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(valueConfigResponse)));
    }

    @Test
    void getValueConfigListTest() throws Exception {
        List<ValueConfigDto> list = new ArrayList<>();
        given(valueConfigService.getValueConfigListByOrganizationId(anyInt())).willReturn(list);

        mockMvc.perform(get("/api/sensor/org/1/value")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(list)));
    }

    @Test
    void deleteValueConfigList() throws Exception {
        mockMvc.perform(delete("/api/sensor/org/1/sensor/testSensor/sensorType/1/value/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
