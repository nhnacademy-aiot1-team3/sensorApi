package live.databo3.sensor.influx.battery.service.impl;

import live.databo3.sensor.influx.battery.measurement.BatteryLevelData;
import live.databo3.sensor.influx.battery.service.BatteryService;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * battery level과 관련된 service
 * @author jihyeon
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BatteryServiceImpl implements BatteryService {
    private final InfluxDBRepository influxDBRepository;

    /**
     * 주어진 조건으로 query문을 날려 BatteryLevelData 리스트를 가져온다
     * @param field influxdb의 field 이름
     * @param branch influxdb의 branch 이름
     * @param endpoint influxdb의 endpoint 이름
     * @return BatteryLevelData(time, place, device, topic, value)의 리스트
     * @since 1.0.0
     */
    @Override
    public ResponseListData<BatteryLevelData> getBatteryLevels(String field, String branch, String endpoint) {
        List<BatteryLevelData> data = influxDBRepository.findLastDataForEveryWhere(field, branch, endpoint, BatteryLevelData.class);
        ResponseListData<BatteryLevelData> responseListData = new ResponseListData<>();
        responseListData.setData(data);

        return responseListData;

    }
}
