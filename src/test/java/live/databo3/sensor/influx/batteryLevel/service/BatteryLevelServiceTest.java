package live.databo3.sensor.influx.batteryLevel.service;

import live.databo3.sensor.influx.battery.measurement.BatteryLevelData;
import live.databo3.sensor.influx.battery.service.impl.BatteryServiceImpl;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BatteryLevelServiceTest {
    @Mock
    private InfluxDBRepository influxDBRepository;

    @InjectMocks
    private BatteryServiceImpl batteryService;

    @Test
    @DisplayName("battery level 조회")
    void getBatteryLevel() {
        BatteryLevelData batteryLevelData1 = new BatteryLevelData();
        BatteryLevelData batteryLevelData2 = new BatteryLevelData();

        List<BatteryLevelData> batteryLevelDataList = new ArrayList<>();
        batteryLevelDataList.add(batteryLevelData1);
        batteryLevelDataList.add(batteryLevelData2);

        ResponseListData<BatteryLevelData> responseListData = new ResponseListData<>();
        responseListData.setData(batteryLevelDataList);

        given(influxDBRepository.findLastDataForEveryWhere(any(), any(), any(), eq(BatteryLevelData.class)))
                .willReturn(List.of(batteryLevelData1, batteryLevelData2));

        ResponseListData<BatteryLevelData> response = batteryService.getBatteryLevels("value", "nhnacademy");
        List<BatteryLevelData> ress = response.getData();
        Assertions.assertEquals(ress.size(), 2);

    }
}
