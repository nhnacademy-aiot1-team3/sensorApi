package live.databo3.sensor.setting_function_type.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.service.SettingFunctionTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SettingFunctionTypeController.class)
class SettingFunctionTypeControllerTest {

    @MockBean
    private SettingFunctionTypeService settingFunctionTypeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getSettingFunctionTest() throws Exception {
        List<SettingFunctionType> list = new ArrayList<>();
        given(settingFunctionTypeService.getSettingFunctionTypes()).willReturn(list);
        mockMvc.perform(get("/api/sensor/settingFunction")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
