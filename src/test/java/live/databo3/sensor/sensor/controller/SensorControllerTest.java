package live.databo3.sensor.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.SensorResponse;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.service.SensorService;
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

@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @MockBean
    private SensorService sensorService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createSensor() throws Exception {
        SensorResponse sensorResponse = new SensorResponse("ts", "testSensor", 1, "sectionA");
        given(sensorService.registerSensor(anyInt(), any())).willReturn(sensorResponse);

        mockMvc.perform(post("/api/sensor/org/1/sensor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterSensorRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(sensorResponse)));
    }

    @Test
    void getSensorList() throws Exception {
        List<SensorDto> list = new ArrayList<>();
        given(sensorService.getSensors(anyInt())).willReturn(list);

        mockMvc.perform(get("/api/sensor/org/1/sensor")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(list)));
    }

    @Test
    void getSensor() throws Exception {
        SensorDto sensorDto = new SensorDto() {
            @Override
            public String getSensorSn() {
                return null;
            }
            @Override
            public String getSensorName() {
                return null;
            }
            @Override
            public PlaceDto getPlace() {
                return null;
            }
        };
        given(sensorService.getSensor(anyInt(), anyString())).willReturn(sensorDto);

        mockMvc.perform(get("/api/sensor/org/1/sensor/ts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void modifySensor() throws Exception {
        SensorResponse sensorResponse = new SensorResponse();
        given(sensorService.modifySensor(anyInt(), anyString(), any())).willReturn(sensorResponse);

        mockMvc.perform(put("/api/sensor/org/1/sensor/ts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ModifySensorRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sensorResponse)));
    }

    @Test
    void deleteSensor() throws Exception {
        mockMvc.perform(delete("/api/sensor/org/1/sensor/ts"))
                .andExpect(status().isOk());
    }
}
