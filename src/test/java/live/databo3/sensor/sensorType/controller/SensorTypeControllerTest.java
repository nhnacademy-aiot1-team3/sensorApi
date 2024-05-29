package live.databo3.sensor.sensorType.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor_type.controller.SensorTypeController;
import live.databo3.sensor.sensor_type.dto.request.ModifySensorTypeRequest;
import live.databo3.sensor.sensor_type.dto.request.RegisterSensorTypeRequest;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.service.SensorTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorTypeController.class)
class SensorTypeControllerTest {

    @MockBean
    private SensorTypeService sensorTypeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getSensorTypeListTest() throws Exception {
        List<SensorType> list = new ArrayList<>();
        given(sensorTypeService.getSensorTypes()).willReturn(list);
        mockMvc.perform(get("/api/sensor/sensorType")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(list)));
    }

    @Test
    void createSensorTypeTest() throws Exception {
        SensorType sensorType = SensorType.builder().build();
        given(sensorTypeService.registerSensorType(any(RegisterSensorTypeRequest.class))).willReturn(sensorType);
        mockMvc.perform(post("/api/sensor/sensorType")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterSensorRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(sensorType)));
    }

    @Test
    void modifySensorTypeTest() throws Exception {
        SensorType sensorType = SensorType.builder().build();
        given(sensorTypeService.modifySensorType(anyInt(), any(ModifySensorTypeRequest.class))).willReturn(sensorType);
        mockMvc.perform(put("/api/sensor/sensorType/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ModifySensorRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sensorType)));
    }

    @Test
    void deleteSensorTypeTest() throws Exception {
        mockMvc.perform(delete("/api/sensor/sensorType/1"))
                .andExpect(status().isOk());
    }
}
