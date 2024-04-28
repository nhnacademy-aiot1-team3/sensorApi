package live.databo3.sensor.settingFunctionType.controller;

import live.databo3.sensor.settingFunctionType.dto.request.ModifySettingFunctionTypeRequest;
import live.databo3.sensor.settingFunctionType.dto.request.RegisterSettingFunctionTypeRequest;
import live.databo3.sensor.settingFunctionType.entity.SettingFunctionType;
import live.databo3.sensor.settingFunctionType.service.SettingFunctionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensor/settingFunction")
public class SettingFunctionTypeController {
    private final SettingFunctionTypeService settingFunctionTypeService;

    @GetMapping
    public ResponseEntity<List<SettingFunctionType>> getSettingFunctionTypeList() {
        return ResponseEntity.ok(settingFunctionTypeService.getSettingFunctionTypes());
    }

    @PostMapping("/register")
    public ResponseEntity<SettingFunctionType> createSettingFunctionType(@RequestBody RegisterSettingFunctionTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(settingFunctionTypeService.registertSettingFunctionType(request));
    }

    @PostMapping("/modify")
    public ResponseEntity<SettingFunctionType> modifySettingFunctionType(@RequestBody ModifySettingFunctionTypeRequest request) {
        return ResponseEntity.ok(settingFunctionTypeService.modifySettingFunctionType(request));
    }

    @DeleteMapping("/{settingFunctionId}")
    public ResponseEntity<Void> deleteSettingFunctionType(@PathVariable Long settingFunctionId) {
        settingFunctionTypeService.deleteSettingFunctionTypes(settingFunctionId);
        return ResponseEntity.ok(null);
    }
}
