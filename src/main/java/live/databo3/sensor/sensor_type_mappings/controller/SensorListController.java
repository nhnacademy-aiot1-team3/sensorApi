package live.databo3.sensor.sensor_type_mappings.controller;

import live.databo3.sensor.sensor_type_mappings.dto.GetSensorInfoResponse;
import live.databo3.sensor.sensor_type_mappings.dto.OrganizationPlaceDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorListForRedisDto;
import live.databo3.sensor.sensor_type_mappings.service.impl.SensorListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 센서리스트들을 반환하기 위한 컨트롤러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor")
public class SensorListController {
    private final SensorListService sensorListService;

    @GetMapping("/sensorInfo")
    public ResponseEntity<Map<Long, GetSensorInfoResponse>> getSensorInfo(@RequestParam List<Long> id) {
        return ResponseEntity.ok(sensorListService.getSensorInfo(id));
    }

    /**
     * 특정 센서타입의 센서들의 목록을 반환합니다.
     *
     * @since : 강경훈
     */
    @GetMapping("/sensorList")
    public ResponseEntity<List<OrganizationPlaceDto>> getSensorList(@RequestParam Integer sensorTypeId) {
        return ResponseEntity.ok(sensorListService.getSensorListBySensorType(sensorTypeId));
    }

    /**
     * 특정 조직의 센서들의 목록을 반환합니다.
     *
     * @since : 강경훈
     */
    @GetMapping("/sensorList/org/{organizationName}")
    public ResponseEntity<List<SensorListForRedisDto>> getSensorListByOrganizationName(@PathVariable String organizationName) {
        return ResponseEntity.ok(sensorListService.getSensorListByOrganizationName(organizationName));
    }
}
