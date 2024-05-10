package live.databo3.sensor.device.controller;

import live.databo3.sensor.device.dto.DeviceDto;
import live.databo3.sensor.device.dto.DeviceResponse;
import live.databo3.sensor.device.dto.ModifyDeviceRequest;
import live.databo3.sensor.device.dto.RegisterDeviceRequest;
import live.databo3.sensor.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * device entity 관련 controller
 * uri 중 organizationId는 조직의 Integer 타입의 id에 해당한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/org/{organizationId}/device")
public class DeviceController {

    private final DeviceService deviceService;
    /**
     * POST 요청을 받아 device 를 등록한다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@PathVariable Integer organizationId, @RequestBody RegisterDeviceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.registerDevice(organizationId, request));
    }

    /**
     * GET 요청을 받아 특정 조직의 device 리스트를 조회한다.
     * @since 1.0.0
     */
    @GetMapping("/all")
    public ResponseEntity<List<DeviceDto>> getDeviceList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(deviceService.getDevices(organizationId));
    }

    /**
     * GET 요청을 받아 특정 device 에 대한 정보를 조회한다.
     * @since 1.0.0
     */
    @GetMapping("/{deviceSn}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable Integer organizationId, @PathVariable String deviceSn) {
        return ResponseEntity.ok(deviceService.getDevice(organizationId, deviceSn));
    }

    /**
     * PUT 요청을 받아 특정 device 에 대해 수정한다.
     * @since 1.0.0
     */
    @PutMapping("/{deviceSn}")
    public ResponseEntity<DeviceResponse> modifyDevice(@PathVariable Integer organizationId, @PathVariable String deviceSn, @RequestBody ModifyDeviceRequest request) {
        return ResponseEntity.ok(deviceService.modifyDevice(organizationId, deviceSn, request));
    }

    /**
     * DELETE 요청을 받아 특정 device 를 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping("/{deviceSn}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer organizationId, @PathVariable String deviceSn) {
        deviceService.deleteDevice(organizationId, deviceSn);
        return ResponseEntity.ok(null);
    }
}
