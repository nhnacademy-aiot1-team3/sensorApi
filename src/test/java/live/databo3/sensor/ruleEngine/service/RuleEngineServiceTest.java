package live.databo3.sensor.ruleEngine.service;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.rule_engine.service.RuleEngineService;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleEngineServiceTest {
    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Mock
    private SensorTypeMappingRepository sensorTypeMappingRepository;

    @Mock
    private GeneralConfigRepository generalConfigRepository;

    @Mock
    private SettingFunctionTypeRepository settingFunctionTypeRepository;

    @InjectMocks
    private RuleEngineService ruleEngineService;

    @Test
    void registerSensorTest() {
        Organization organization = new Organization(1, "testOrganizationName", "testGateway", "testController");
        when(organizationRepository.findByOrganizationName(anyString())).thenReturn(Optional.of(organization));

        Place place = new Place(1, "testPlace", organization);
        when(placeRepository.findByPlaceNameAndOrganization_OrganizationName(anyString(), anyString())).thenReturn(Optional.of(place));

        Sensor sensor = new Sensor("testSensorSn", "testSensorName", place, organization);
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(sensor));

        SensorType sensorType = new SensorType(1, "testSensorType");
        when(sensorTypeRepository.findBySensorType(anyString())).thenReturn(Optional.of(sensorType));

        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);

        SensorTypeMappings sensorTypeMappings = new SensorTypeMappings(1L, sensor, sensorType);
        when(sensorTypeMappingRepository.saveAndFlush(any(SensorTypeMappings.class))).thenReturn(sensorTypeMappings);

        SettingFunctionType settingFunctionType = new SettingFunctionType(1L, SettingFunctionType.SETTINGFUNCTIONTYPE.READ_ONLY);
        when(settingFunctionTypeRepository.findById(anyLong())).thenReturn(Optional.of(settingFunctionType));

        GeneralConfig generalConfig = new GeneralConfig(null, sensorTypeMappings, settingFunctionType, null, LocalDateTime.now());
        when(generalConfigRepository.save(any(GeneralConfig.class))).thenReturn(generalConfig);

        ruleEngineService.registerSensor(organization.getOrganizationName(), place.getPlaceName(), sensor.getSensorSn(), sensorType.getSensorType());

        verify(organizationRepository, times(1)).findByOrganizationName(anyString());
        verify(placeRepository, times(1)).findByPlaceNameAndOrganization_OrganizationName(anyString(), anyString());
        verify(sensorRepository, times(1)).findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt());
        verify(sensorTypeMappingRepository, times(1)).existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        verify(sensorTypeMappingRepository, times(1)).saveAndFlush(any(SensorTypeMappings.class));
        verify(settingFunctionTypeRepository, times(1)).findById(anyLong());
        verify(generalConfigRepository, times(1)).save(any(GeneralConfig.class));
    }
}
