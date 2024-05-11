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

/**
 * settingFunction entity 관련 controller
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/sensor/settingFunction")
public class SettingFunctionTypeController {
    private final SettingFunctionTypeService settingFunctionTypeService;

    /**
     * GET 요청을 받아 settingFunction 을 조회한다.
     * @since 1.0.0
     */
    @GetMapping
    public ResponseEntity<List<SettingFunctionType>> getSettingFunctionTypeList() {
        return ResponseEntity.ok(settingFunctionTypeService.getSettingFunctionTypes());
    }

    /**
     * POST 요청을 받아 settingFunction 을 등록한다.
     * @since 1.0.0
     */
    @PostMapping
    public ResponseEntity<SettingFunctionType> createSettingFunctionType(@RequestBody RegisterSettingFunctionTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(settingFunctionTypeService.registertSettingFunctionType(request));
    }

    /**
     * PUT 요청을 받아 settingFunction 을 수정한다.
     * @since 1.0.0
     */
    @PutMapping("/{settingFunctionId}")
    public ResponseEntity<SettingFunctionType> modifySettingFunctionType(@PathVariable Long settingFunctionId, @RequestBody ModifySettingFunctionTypeRequest request) {
        return ResponseEntity.ok(settingFunctionTypeService.modifySettingFunctionType(settingFunctionId, request));
    }

    /**
     * DELETE 요청을 받아 settingFunction 을 삭제한다.
     * @since 1.0.0
     */
    @DeleteMapping("/{settingFunctionId}")
    public ResponseEntity<Void> deleteSettingFunctionType(@PathVariable Long settingFunctionId) {
        settingFunctionTypeService.deleteSettingFunctionTypes(settingFunctionId);
        return ResponseEntity.ok(null);
    }
}
