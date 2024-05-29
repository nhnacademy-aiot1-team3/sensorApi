package live.databo3.sensor.influx.battery.service;

import live.databo3.sensor.influx.battery.measurement.BatteryLevelData;
import live.databo3.sensor.influx.dto.ResponseListData;

public interface BatteryService {
    ResponseListData<BatteryLevelData> getBatteryLevels(String fieldType, String branchName, String endpoint);
}
