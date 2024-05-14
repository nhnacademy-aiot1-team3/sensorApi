package live.databo3.sensor.device.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.device.dto.DeviceDto;
import live.databo3.sensor.device.dto.DeviceResponse;
import live.databo3.sensor.device.dto.ModifyDeviceRequest;
import live.databo3.sensor.device.dto.RegisterDeviceRequest;
import live.databo3.sensor.device.service.DeviceService;
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

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @MockBean
    private DeviceService deviceService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void createDeviceTest() throws Exception {
        DeviceResponse deviceResponse = new DeviceResponse("td", "testDevice");
        given(deviceService.registerDevice(anyInt(), any()))
                .willReturn(deviceResponse);
        mockMvc.perform(post("/api/sensor/org/1/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterDeviceRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceResponse)));
    }

    @Test
    void getDeviceListTest() throws Exception {
        List<DeviceDto> list = new ArrayList<>();
        given(deviceService.getDevices(anyInt())).willReturn(list);
        mockMvc.perform(get("/api/sensor/org/1/device")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(list)));
    }

    @Test
    void getDeviceTest() throws Exception {
        DeviceResponse deviceResponse = new DeviceResponse("td", "testDevice");
        given(deviceService.getDevice(anyInt(), anyString())).willReturn(deviceResponse);
        mockMvc.perform(get("/api/sensor/org/1/device/td")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceResponse)));
    }

    @Test
    void modifyDeviceTest() throws Exception {
        DeviceResponse deviceResponse = new DeviceResponse("td", "testDevice");
        given(deviceService.modifyDevice(anyInt(), anyString(), any())).willReturn(deviceResponse);
        mockMvc.perform(put("/api/sensor/org/1/device/td")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ModifyDeviceRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceResponse)));
    }

    @Test
    void deleteDeviceTest() throws Exception {
        mockMvc.perform(delete("/api/sensor/org/1/device/td"))
                .andExpect(status().isOk());
    }
}
