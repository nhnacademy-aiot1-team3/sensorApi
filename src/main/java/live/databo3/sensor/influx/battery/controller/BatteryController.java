package live.databo3.sensor.influx.battery.controller;

import live.databo3.sensor.influx.battery.measurement.BatteryLevelData;
import live.databo3.sensor.influx.battery.service.BatteryService;
import live.databo3.sensor.influx.dto.ResponseListData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * battery level과 관련된 controller
 * @author jihyeon
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/sensor/battery-levels/fields/{fieldType}/branches/{branchName}/endpoint/battery_level")
@RequiredArgsConstructor
public class BatteryController {

    private final BatteryService batteryService;

    /**
     * 주어진 조건에 따른 sensor들의 battery level을 알려주는 메소드
     * @param fieldType influxdb의 field 이름
     * @param branchName influxdb의 branch 이름
     * @return BatteryLevelData(time, place, device, topic, value)의 리스트
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<ResponseListData<BatteryLevelData>> getBattertLevels(@PathVariable String fieldType, @PathVariable String branchName) {
        return ResponseEntity.status(HttpStatus.OK).body(batteryService.getBatteryLevels(fieldType, branchName));
    }
}
