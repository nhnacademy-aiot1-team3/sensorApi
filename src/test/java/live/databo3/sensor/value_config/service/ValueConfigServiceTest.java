package live.databo3.sensor.value_config.service;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.exception.already_exist_exception.ValueConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.exception.not_exist_exception.ValueConfigNotExistException;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.value_config.dto.ValueConfigDto;
import live.databo3.sensor.value_config.dto.ValueConfigRequest;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
import live.databo3.sensor.value_config.entity.ValueConfig;
import live.databo3.sensor.value_config.repository.ValueConfigRepository;
import live.databo3.sensor.value_config.service.impl.ValueConfigServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValueConfigServiceTest {
    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @Mock
    private GeneralConfigRepository generalConfigRepository;

    @Mock
    private ValueConfigRepository valueConfigRepository;

    @InjectMocks
    private ValueConfigServiceImpl valueConfigService;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorType sensorType;
    SensorTypeMappings sensorTypeMappings;
    SettingFunctionType settingFunctionType;
    Device device;
    GeneralConfig generalConfig;
    ValueConfig valueConfig;
    ValueConfigResponse valueConfigResponse;
    ValueConfigRequest valueConfigRequest;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
                .organizationId(1)
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .placeName("testPlace")
                .build();

        sensor = Sensor.builder()
                .sensorSn("testSensorSn")
                .sensorName("testSensorName")
                .place(place)
                .organization(organization)
                .build();

        sensorType = SensorType.builder()
                .sensorTypeId(1)
                .sensorType("testSensorType")
                .build();

        sensorTypeMappings = new SensorTypeMappings(null, sensor, sensorType);

        settingFunctionType = new SettingFunctionType(null, SettingFunctionType.SETTINGFUNCTIONTYPE.READ_ONLY);

        device = Device.builder()
                .deviceSn("testDeviceSn")
                .organization(organization)
                .deviceName("testDeviceName")
                .build();

        generalConfig = new GeneralConfig(null, sensorTypeMappings, settingFunctionType, device, null);
        valueConfig = new ValueConfig(1L, "firstEntry", "secondEntry", generalConfig);

        valueConfigResponse = valueConfig.toDto();

        valueConfigRequest = new ValueConfigRequest();
        ReflectionTestUtils.setField(valueConfigRequest, "firstEntry", "firstEntry");
        ReflectionTestUtils.setField(valueConfigRequest, "secondEntry", "secondEntry");
    }

    @Test
    void registerValueConfigSuccessTest() {
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.of(sensorType));
        when(valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(generalConfig));
        when(valueConfigRepository.save(any(ValueConfig.class))).thenReturn(valueConfig);

        ValueConfigResponse response = valueConfigService.createValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfigRequest);

        assertEquals(response.getFirstEntry(), valueConfigResponse.getFirstEntry());
        assertEquals(response.getSecondEntry(), valueConfigResponse.getSecondEntry());
        assertEquals(response.getRecordNumber(), valueConfigResponse.getRecordNumber());
        assertEquals(response.getValueConfigNumber(), valueConfigResponse.getValueConfigNumber());
    }

    @Test
    void registerValueConfigFailTest() {
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> valueConfigService.createValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfigRequest))
                .isInstanceOf(SensorTypeNotExistException.class)
                .hasMessageContaining(SensorTypeNotExistException.MESSAGE);

    }

    @Test
    void registerValueConfigFail2Test() {
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.of(sensorType));
        when(valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(true);

        assertThatThrownBy(() -> valueConfigService.createValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfigRequest))
                .isInstanceOf(ValueConfigAlreadyExistException.class)
                .hasMessageContaining(ValueConfigAlreadyExistException.MESSAGE);
    }

    @Test
    void registerValueConfigFail3Test() {
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.of(sensorType));
        when(valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> valueConfigService.createValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfigRequest))
                .isInstanceOf(GeneralConfigNotExistException.class)
                .hasMessageContaining(GeneralConfigNotExistException.MESSAGE);
    }

    @Test
    void modifyValueConfigSuccessTest() {
        when(valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(anyString(), anyInt(), anyInt(), anyLong())).thenReturn(Optional.of(valueConfig));

        ValueConfigResponse response = valueConfigService.modifyValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfig.getValueConfigNumber(), valueConfigRequest);

        assertEquals(response.getValueConfigNumber(), valueConfigResponse.getValueConfigNumber());
        assertEquals(response.getRecordNumber(), valueConfigResponse.getRecordNumber());
        assertEquals(response.getFirstEntry(), valueConfigResponse.getFirstEntry());
        assertEquals(response.getSecondEntry(), valueConfigResponse.getSecondEntry());
    }

    @Test
    void modifyValueConfigFailTest() {
        when(valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(anyString(), anyInt(), anyInt(), anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> valueConfigService.modifyValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfig.getValueConfigNumber(), valueConfigRequest))
                .isInstanceOf(ValueConfigNotExistException.class)
                .hasMessageContaining(ValueConfigNotExistException.MESSAGE);
    }

    @Test
    void deleteValueConfigSuccessTest() {
        when(valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(true);
        when(valueConfigRepository.existsById(anyLong())).thenReturn(true);

        valueConfigService.deleteValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfig.getValueConfigNumber());
        verify(valueConfigRepository, times(1)).existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
    }

    @Test
    void deleteValueConfigFailTest() {
        when(valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);

        assertThatThrownBy(() -> valueConfigService.deleteValueConfig(
                organization.getOrganizationId(),
                sensor.getSensorSn(),
                sensorType.getSensorTypeId(),
                valueConfig.getValueConfigNumber()))
                .isInstanceOf(ValueConfigNotExistException.class)
                .hasMessageContaining(ValueConfigNotExistException.MESSAGE);
    }

    @Test
    void deleteValueConfigFail2Test() {
        when(valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(true);
        when(valueConfigRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> valueConfigService.deleteValueConfig(
                organization.getOrganizationId(),
                sensor.getSensorSn(),
                sensorType.getSensorTypeId(),
                valueConfig.getValueConfigNumber()))
                .isInstanceOf(ValueConfigNotExistException.class)
                .hasMessageContaining(ValueConfigNotExistException.MESSAGE);
    }

    @Test
    void getValueConfigSuccessTest() {
        when(valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(anyString(), anyInt(), anyInt(), anyLong())).thenReturn(Optional.of(valueConfig));

        ValueConfigResponse response = valueConfigService.getValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfig.getValueConfigNumber());

        assertEquals(response.getValueConfigNumber(), valueConfigResponse.getValueConfigNumber());
        assertEquals(response.getRecordNumber(), valueConfigResponse.getRecordNumber());
        assertEquals(response.getFirstEntry(), valueConfigResponse.getFirstEntry());
        assertEquals(response.getSecondEntry(), valueConfigResponse.getSecondEntry());
        verify(valueConfigRepository, times(1)).findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(anyString(), anyInt(), anyInt(), anyLong());
    }

    @Test
    void getValueConfigFailTest() {
        when(valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(anyString(), anyInt(), anyInt(), anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> valueConfigService.getValueConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), valueConfig.getValueConfigNumber()))
                .isInstanceOf(ValueConfigNotExistException.class)
                .hasMessageContaining(ValueConfigNotExistException.MESSAGE);
    }

    @Test
    void getValueConfigListTest() {
        when(valueConfigRepository.findAllByGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationId(anyInt())).thenReturn(List.of(valueConfig));

        List<ValueConfigDto> list = valueConfigService.getValueConfigListByOrganizationId(organization.getOrganizationId());

        assertEquals(list.get(0).getFirstEntry(), valueConfig.toDto().getFirstEntry());
        assertEquals(list.get(0).getSecondEntry(), valueConfig.toDto().getSecondEntry());

        verify(valueConfigRepository, times(1)).findAllByGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationId(anyInt());
    }
}
