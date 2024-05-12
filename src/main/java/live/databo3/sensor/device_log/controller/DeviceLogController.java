package live.databo3.sensor.device_log.controller;

import live.databo3.sensor.annotations.CheckPermission;
import live.databo3.sensor.device_log.dto.DeviceLogCreateRequestDto;
import live.databo3.sensor.device_log.dto.DeviceLogResponseDto;
import live.databo3.sensor.device_log.service.DeviceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor/device/log")
@RequiredArgsConstructor
public class DeviceLogController {
    private final DeviceLogService deviceLogService;


    @PostMapping
    public ResponseEntity<DeviceLogResponseDto> saveDeviceLog(@RequestBody DeviceLogCreateRequestDto deviceLogCreateRequestDto) {

        DeviceLogResponseDto deviceLog = deviceLogService.createDeviceLog(deviceLogCreateRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deviceLog);
    }

    @GetMapping("/org/{organizationId}")
    @CheckPermission
    public ResponseEntity<List<DeviceLogResponseDto>> getDeviceLogByOrganizationId(@PathVariable("organizationId") Integer organizationId) {
        List<DeviceLogResponseDto> deviceLogs = deviceLogService.getDeviceLogs(organizationId);
        return ResponseEntity.ok(deviceLogs);
    }

}
