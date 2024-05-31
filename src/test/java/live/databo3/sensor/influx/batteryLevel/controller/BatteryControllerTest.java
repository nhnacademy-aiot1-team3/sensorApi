package live.databo3.sensor.influx.batteryLevel.controller;

import live.databo3.sensor.influx.battery.controller.BatteryController;
import live.databo3.sensor.influx.battery.measurement.BatteryLevelData;
import live.databo3.sensor.influx.battery.service.BatteryService;
import live.databo3.sensor.influx.dto.ResponseListData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BatteryController.class)
@AutoConfigureMockMvc
public class BatteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatteryService batteryService;

    @Test
    @DisplayName("battery level 조회")
    void getBatteryLevel() throws Exception {

        BatteryLevelData batteryLevelData1 = new BatteryLevelData();
        BatteryLevelData batteryLevelData2 = new BatteryLevelData();

        List<BatteryLevelData> batteryLevelDataList = new ArrayList<>();
        batteryLevelDataList.add(batteryLevelData1);
        batteryLevelDataList.add(batteryLevelData2);

        ResponseListData <BatteryLevelData> responseListData = new ResponseListData<>();
        responseListData.setData(batteryLevelDataList);

        given(batteryService.getBatteryLevels(any(String.class), any(String.class))).willReturn(responseListData);

        mockMvc.perform(get("/api/sensor/battery-levels/fields/value/branches/nhnacademy/endpoint/battery_level")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(batteryService, times(1)).getBatteryLevels(any(), any());
    }
}
