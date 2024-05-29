package live.databo3.sensor.setting_function_type.service;

import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import live.databo3.sensor.setting_function_type.service.impl.SettingFunctionTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SettingFunctionTypeServiceTest {

    @Mock
    private SettingFunctionTypeRepository settingFunctionTypeRepository;

    @InjectMocks
    private SettingFunctionTypeServiceImpl settingFunctionTypeService;

    @Test
    void getSettingFunctionTypeListTest() {
        settingFunctionTypeService.getSettingFunctionTypes();

        verify(settingFunctionTypeRepository, times(1)).findAll();
    }
}
