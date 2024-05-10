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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensor/org/{organizationId}/device")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@PathVariable Integer organizationId, @RequestBody RegisterDeviceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.registerDevice(organizationId, request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDto>> getDeviceList(@PathVariable Integer organizationId) {
        return ResponseEntity.ok(deviceService.getDevices(organizationId));
    }

    @GetMapping("/{deviceSn}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable Integer organizationId, @PathVariable String deviceSn) {
        return ResponseEntity.ok(deviceService.getDevice(organizationId, deviceSn));
    }

    @PutMapping("/{deviceSn}")
    public ResponseEntity<DeviceResponse> modifyDevice(@PathVariable Integer organizationId, @PathVariable String deviceSn, @RequestBody ModifyDeviceRequest request) {
        return ResponseEntity.ok(deviceService.modifyDevice(organizationId, deviceSn, request));
    }

    @DeleteMapping("/{deviceSn}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Integer organizationId, @PathVariable String deviceSn) {
        deviceService.deleteDevice(organizationId, deviceSn);
        return ResponseEntity.ok(null);
    }
}
