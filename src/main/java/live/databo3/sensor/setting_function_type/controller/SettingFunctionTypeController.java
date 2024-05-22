package live.databo3.sensor.setting_function_type.controller;

import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.service.SettingFunctionTypeService;
import lombok.RequiredArgsConstructor;
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
}
