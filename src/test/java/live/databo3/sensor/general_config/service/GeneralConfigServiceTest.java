package live.databo3.sensor.general_config.service;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.device.repository.DeviceRepository;
import live.databo3.sensor.exception.already_exist_exception.GeneralConfigAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.DeviceNotExistException;
import live.databo3.sensor.exception.not_exist_exception.GeneralConfigNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeMappingNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.general_config.dto.GeneralConfigDto;
import live.databo3.sensor.general_config.dto.request.ModifyGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.request.RegisterGeneralConfigRequest;
import live.databo3.sensor.general_config.dto.response.GeneralConfigResponse;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.general_config.service.impl.GeneralConfigServiceImpl;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
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
class GeneralConfigServiceTest {
    @Mock
    private GeneralConfigRepository generalConfigRepository;

    @Mock
    private SensorTypeMappingRepository sensorTypeMappingRepository;

    @Mock
    private SettingFunctionTypeRepository settingFunctionTypeRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private GeneralConfigServiceImpl generalConfigService;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorType sensorType;
    SensorTypeMappings sensorTypeMappings;
    SettingFunctionType settingFunctionType;
    Device device;
    GeneralConfig generalConfig;

    RegisterGeneralConfigRequest registerGeneralConfigRequest;
    ModifyGeneralConfigRequest modifyGeneralConfigRequest;

    GeneralConfigResponse generalConfigResponse;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
                .organizationId(1)
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .placeId(1)
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

        sensorTypeMappings = new SensorTypeMappings(1L, sensor, sensorType);

        settingFunctionType = new SettingFunctionType(1L, SettingFunctionType.SETTINGFUNCTIONTYPE.READ_ONLY);

        device = Device.builder()
                .deviceSn("testDeviceSn")
                .organization(organization)
                .deviceName("testDeviceName")
                .build();

        generalConfig = new GeneralConfig(1L, sensorTypeMappings, settingFunctionType, device, null);

        generalConfigResponse = generalConfig.toDto();

        registerGeneralConfigRequest = new RegisterGeneralConfigRequest();
        ReflectionTestUtils.setField(registerGeneralConfigRequest, "functionId", 1L);
        ReflectionTestUtils.setField(registerGeneralConfigRequest, "deviceSn", "testDeviceSn");

        modifyGeneralConfigRequest = new ModifyGeneralConfigRequest();
        ReflectionTestUtils.setField(modifyGeneralConfigRequest, "functionId", 1L);
        ReflectionTestUtils.setField(modifyGeneralConfigRequest, "deviceSn", "testDeviceSn");
    }

    @Test
    void registerGeneralConfigSuccessTest() {
        doReturn(false)
                .when(generalConfigRepository)
                .existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(sensorTypeMappings))
                .when(sensorTypeMappingRepository).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(settingFunctionType)).when(settingFunctionTypeRepository).findById(anyLong());
        doReturn(Optional.of(device)).when(deviceRepository).findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt());
        when(generalConfigRepository.save(any(GeneralConfig.class))).thenReturn(generalConfig);

        GeneralConfigResponse response = generalConfigService.registerGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), registerGeneralConfigRequest);

        assertEquals(response.getDeviceSn(), generalConfigResponse.getDeviceSn());
        assertEquals(response.getRecordNumber(), generalConfigResponse.getRecordNumber());
        assertEquals(response.getFunctionId(), generalConfigResponse.getFunctionId());
        assertEquals(response.getLastUpdateDate(), generalConfigResponse.getLastUpdateDate());
    }

    @Test
    void registerGeneralConfigSuccess2Test() {
        doReturn(false)
                .when(generalConfigRepository)
                .existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(sensorTypeMappings))
                .when(sensorTypeMappingRepository).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(settingFunctionType)).when(settingFunctionTypeRepository).findById(anyLong());
        when(generalConfigRepository.save(any(GeneralConfig.class))).thenReturn(generalConfig);

        ReflectionTestUtils.setField(registerGeneralConfigRequest, "deviceSn", "");

        GeneralConfigResponse response = generalConfigService.registerGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), registerGeneralConfigRequest);

        assertEquals(response.getDeviceSn(), generalConfigResponse.getDeviceSn());
        assertEquals(response.getRecordNumber(), generalConfigResponse.getRecordNumber());
        assertEquals(response.getFunctionId(), generalConfigResponse.getFunctionId());
        assertEquals(response.getLastUpdateDate(), generalConfigResponse.getLastUpdateDate());
    }
    @Test
    void registerGeneralConfigFailTest() {
        doReturn(true)
                .when(generalConfigRepository)
                .existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());

        assertThatThrownBy(() -> generalConfigService.registerGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), registerGeneralConfigRequest))
                .isInstanceOf(GeneralConfigAlreadyExistException.class)
                .hasMessageContaining(GeneralConfigAlreadyExistException.MESSAGE);
    }

    @Test
    void registerGeneralConfigFail2Test() {
        doReturn(false)
                .when(generalConfigRepository)
                .existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.empty())
                .when(sensorTypeMappingRepository).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());

        assertThatThrownBy(() -> generalConfigService.registerGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), registerGeneralConfigRequest))
                .isInstanceOf(SensorTypeMappingNotExistException.class)
                .hasMessageContaining(SensorTypeMappingNotExistException.MESSAGE);
    }

    @Test
    void registerGeneralConfigFail3Test() {
        doReturn(false)
                .when(generalConfigRepository)
                .existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(sensorTypeMappings))
                .when(sensorTypeMappingRepository).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.empty()).when(settingFunctionTypeRepository).findById(anyLong());

        assertThatThrownBy(() -> generalConfigService.registerGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), registerGeneralConfigRequest))
                .isInstanceOf(SettingFunctionTypeNotExistException.class)
                .hasMessageContaining(SettingFunctionTypeNotExistException.MESSAGE);
    }

    @Test
    void registerGeneralConfigFail4Test() {
        doReturn(false)
                .when(generalConfigRepository)
                .existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(sensorTypeMappings))
                .when(sensorTypeMappingRepository).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        doReturn(Optional.of(settingFunctionType)).when(settingFunctionTypeRepository).findById(anyLong());
        doReturn(Optional.empty()).when(deviceRepository).findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt());

        assertThatThrownBy(() -> generalConfigService.registerGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), registerGeneralConfigRequest))
                .isInstanceOf(DeviceNotExistException.class)
                .hasMessageContaining(DeviceNotExistException.MESSAGE);
    }

    @Test
    void modifyGeneralConfigSuccessTest() {
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(generalConfig));
        when(settingFunctionTypeRepository.findById(anyLong())).thenReturn(Optional.of(settingFunctionType));
        when(deviceRepository.findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(device));

        GeneralConfigResponse response = generalConfigService.modifyGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), modifyGeneralConfigRequest);

        assertEquals(response.getDeviceSn(), generalConfigResponse.getDeviceSn());
        assertEquals(response.getFunctionId(), generalConfigResponse.getFunctionId());
    }

    @Test
    void modifyGeneralConfigFailTest() {
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> generalConfigService.modifyGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), modifyGeneralConfigRequest))
                .isInstanceOf(GeneralConfigNotExistException.class)
                .hasMessageContaining(GeneralConfigNotExistException.MESSAGE);
    }

    @Test
    void modifyGeneralConfigFailT2est() {
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(generalConfig));
        when(settingFunctionTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> generalConfigService.modifyGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), modifyGeneralConfigRequest))
                .isInstanceOf(SettingFunctionTypeNotExistException.class)
                .hasMessageContaining(SettingFunctionTypeNotExistException.MESSAGE);
    }

    @Test
    void modifyGeneralConfigFail3Test() {
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(generalConfig));
        when(settingFunctionTypeRepository.findById(anyLong())).thenReturn(Optional.of(settingFunctionType));
        when(deviceRepository.findByDeviceSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> generalConfigService.modifyGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId(), modifyGeneralConfigRequest))
                .isInstanceOf(DeviceNotExistException.class)
                .hasMessageContaining(DeviceNotExistException.MESSAGE);
    }

    @Test
    void findGeneralConfigByOrganizationIdTest() {
        List<GeneralConfigDto> list = generalConfigService.findGeneralConfigByOrganizationId(1);

        verify(generalConfigRepository, times(1)).findAllBySensorTypeMappings_Sensor_Organization_OrganizationId(anyInt());
    }

    @Test
    void getGeneralConfigSuccessTest() {
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(generalConfig));
        generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensor.getSensorSn(), organization.getOrganizationId(),sensorType.getSensorTypeId());

        verify(generalConfigRepository, times(1)).findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
    }

    @Test
    void getGeneralConfigFailTest() {
        when(generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> generalConfigService.getGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId()))
                .isInstanceOf(GeneralConfigNotExistException.class)
                .hasMessageContaining(GeneralConfigNotExistException.MESSAGE);
    }

    @Test
    void deleteGeneralConfigSuccessTest() {
        when(generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(true);

        generalConfigService.deleteGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId());
        verify(generalConfigRepository, times(1)).existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        verify(generalConfigRepository, times(1)).deleteBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt());
    }

    @Test
    void deleteGeneralConfigFailTest() {
        when(generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);

        assertThatThrownBy(() -> generalConfigService.deleteGeneralConfig(organization.getOrganizationId(), sensor.getSensorSn(), sensorType.getSensorTypeId()))
                .isInstanceOf(GeneralConfigNotExistException.class)
                .hasMessageContaining(GeneralConfigNotExistException.MESSAGE);
    }
}
