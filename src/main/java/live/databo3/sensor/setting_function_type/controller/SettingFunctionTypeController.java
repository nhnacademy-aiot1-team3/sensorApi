package live.databo3.sensor.setting_function_type.controller;

import live.databo3.sensor.setting_function_type.dto.request.ModifySettingFunctionTypeRequest;
import live.databo3.sensor.setting_function_type.dto.request.RegisterSettingFunctionTypeRequest;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.service.SettingFunctionTypeService;
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

    @PostMapping
    public ResponseEntity<SettingFunctionType> createSettingFunctionType(@RequestBody RegisterSettingFunctionTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(settingFunctionTypeService.registertSettingFunctionType(request));
    }

    @PutMapping("/{settingFunctionId}")
    public ResponseEntity<SettingFunctionType> modifySettingFunctionType(@PathVariable Long settingFunctionId, @RequestBody ModifySettingFunctionTypeRequest request) {
        return ResponseEntity.ok(settingFunctionTypeService.modifySettingFunctionType(settingFunctionId, request));
    }

    @DeleteMapping("/{settingFunctionId}")
    public ResponseEntity<Void> deleteSettingFunctionType(@PathVariable Long settingFunctionId) {
        settingFunctionTypeService.deleteSettingFunctionTypes(settingFunctionId);
        return ResponseEntity.ok(null);
    }
}
