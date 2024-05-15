package live.databo3.sensor.sensor_type_mappings.controller;

import live.databo3.sensor.sensor_type_mappings.dto.OrganizationPlaceDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorListForRedisDto;
import live.databo3.sensor.sensor_type_mappings.service.impl.SensorListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/sensorList")
public class SensorListController {
    private final SensorListService sensorListService;
    @GetMapping
    public ResponseEntity<List<OrganizationPlaceDto>> getSensorList(@RequestParam Integer sensorTypeId) {
        return ResponseEntity.ok(sensorListService.getSensorListBySensorType(sensorTypeId));
    }

    @GetMapping("/org/{organizationName}")
    public ResponseEntity<List<SensorListForRedisDto>> getSensorListByOrganizationName(@PathVariable String organizationName) {
        return ResponseEntity.ok(sensorListService.getSensorListByOrganizationName(organizationName));
    }
}
