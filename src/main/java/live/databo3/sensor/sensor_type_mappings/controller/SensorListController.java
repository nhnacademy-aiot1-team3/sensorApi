package live.databo3.sensor.sensor_type_mappings.controller;

import live.databo3.sensor.sensor_type_mappings.dto.OrganizationPlaceDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorListForRedisDto;
import live.databo3.sensor.sensor_type_mappings.service.impl.SensorListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 센서리스트들을 반환하기 위한 컨트롤러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/sensorList")
public class SensorListController {
    private final SensorListService sensorListService;

    /**
     * 특정 센서타입의 센서들의 목록을 반환합니다.
     *
     * @since : 강경훈
     */
    @GetMapping
    public ResponseEntity<List<OrganizationPlaceDto>> getSensorList(@RequestParam Integer sensorTypeId) {
        return ResponseEntity.ok(sensorListService.getSensorListBySensorType(sensorTypeId));
    }

    /**
     * 특정 조직의 센서들의 목록을 반환합니다.
     *
     * @since : 강경훈
     */
    @GetMapping("/org/{organizationName}")
    public ResponseEntity<List<SensorListForRedisDto>> getSensorListByOrganizationName(@PathVariable String organizationName) {
        return ResponseEntity.ok(sensorListService.getSensorListByOrganizationName(organizationName));
    }
}
