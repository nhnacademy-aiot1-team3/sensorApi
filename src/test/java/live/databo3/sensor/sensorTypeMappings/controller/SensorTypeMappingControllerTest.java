package live.databo3.sensor.sensorTypeMappings.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.sensor_type_mappings.controller.SensorTypeMappingController;
import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SensorTypeMappingController.class)
class SensorTypeMappingControllerTest {

    @MockBean
    private SensorTypeMappingService sensorTypeMappingService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createSensorTypeMappingsTest() throws Exception {
        SensorTypeMappingResponse response = new SensorTypeMappingResponse(1L, "testSensorSn", 1);
        given(sensorTypeMappingService.registerSensorTypeMapping(anyString(), anyInt(), anyInt())).willReturn(response);
        mockMvc.perform(post("/api/sensor/org/1/sensor/testSensorSn/sensorTypeId/1/sensorTypeMapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void modifySensorTypeMappingTest() throws Exception {
        SensorTypeMappingResponse response = new SensorTypeMappingResponse(1L, "testSensorSn", 1);
        given(sensorTypeMappingService.modifySensorTypeMapping(anyString(), anyInt(), anyInt(), any(ModifySensorTypeMappingRequest.class))).willReturn(response);
        mockMvc.perform(put("/api/sensor/org/1/sensor/testSensorSn/sensorTypeId/1/sensorTypeMapping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ModifySensorTypeMappingRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getSensorTypeMappingTest() throws Exception {
        SensorTypeMappingResponse response = new SensorTypeMappingResponse(1L, "testSensorSn", 1);
        given(sensorTypeMappingService.getSensorTypeMapping(anyString(), anyInt(), anyInt())).willReturn(response);
        mockMvc.perform(get("/api/sensor/org/1/sensor/testSensorSn/sensorTypeId/1/sensorTypeMapping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void deleteSensorTypeMappingTest() throws Exception {
        mockMvc.perform(delete(("/api/sensor/org/1/sensor/testSensorSn/sensorTypeId/1/sensorTypeMapping")))
                .andExpect(status().isOk());
    }
}
