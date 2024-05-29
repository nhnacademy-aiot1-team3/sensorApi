package live.databo3.sensor.general_config.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.request.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.service.GeneralConfigService;
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

@WebMvcTest(GeneralConfigController.class)
class GeneralConfigControllerTest {

    @MockBean
    private GeneralConfigService generalConfigService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createGeneralConfigTest() throws Exception {
        GeneralConfigResponse generalConfigResponse = new GeneralConfigResponse(null, 1L, null, "testDeviceSn", "testDeviceName");
        given(generalConfigService.registerGeneralConfig(anyInt(), anyString(), anyInt(), any(RegisterGeneralConfigRequest.class))).willReturn(generalConfigResponse);

        mockMvc.perform(post("/api/sensor/org/1/sensor/testSensorSn/sensorType/1/general")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RegisterGeneralConfigRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(generalConfigResponse)));
    }

    @Test
    void modifyGeneralConfigTest() throws Exception {
        GeneralConfigResponse generalConfigResponse = new GeneralConfigResponse(null, 1L, null, "testDeviceSn", "testDeviceName");
        given(generalConfigService.modifyGeneralConfig(anyInt(), anyString(), anyInt(), any(ModifyGeneralConfigRequest.class))).willReturn(generalConfigResponse);

        mockMvc.perform(put("/api/sensor/org/1/sensor/testSensorSn/sensorType/1/general")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ModifyGeneralConfigRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(generalConfigResponse)));
    }

    @Test
    void getGeneralConfigTest() throws Exception {
        GeneralConfigResponse generalConfigResponse = new GeneralConfigResponse(null, 1L, null, "testDeviceSn", "testDeviceName");
        given(generalConfigService.getGeneralConfig(anyInt(), anyString(), anyInt())).willReturn(generalConfigResponse);

        mockMvc.perform(get("/api/sensor/org/1/sensor/testSensorSn/sensorType/1/general")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(generalConfigResponse)));
    }

    @Test
    void getGeneralConfigListTest() throws Exception {
        List<GeneralConfigDto> list = new ArrayList<>();
        given(generalConfigService.findGeneralConfigByOrganizationId(anyInt())).willReturn(list);

        mockMvc.perform(get("/api/sensor/org/1/general")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(list)));
    }

    @Test
    void deleteGeneralConfigTest() throws Exception {
        mockMvc.perform(get("/api/sensor/org/1/general"))
                .andExpect(status().isOk());
    }
}
