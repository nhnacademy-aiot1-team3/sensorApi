package live.databo3.sensor.sensorTypeMappings.service;

import live.databo3.sensor.exception.already_exist_exception.SensorTypeMappingAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeMappingNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.dto.ModifySensorTypeMappingRequest;
import live.databo3.sensor.sensor_type_mappings.dto.SensorTypeMappingResponse;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.sensor_type_mappings.service.impl.SensorTypeMappingServiceImpl;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorTypeMappingServiceTest {

    @Mock
    private SensorTypeMappingRepository sensorTypeMappingRepository;
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private SensorTypeRepository sensorTypeRepository;
    @Mock
    private GeneralConfigRepository generalConfigRepository;
    @Mock
    private SettingFunctionTypeRepository settingFunctionTypeRepository;

    @InjectMocks
    private SensorTypeMappingServiceImpl sensorTypeMappingService;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorType sensorType;
    SensorTypeMappings sensorTypeMappings;
    SensorTypeMappingResponse sensorTypeMappingResponse;

    ModifySensorTypeMappingRequest modifySensorTypeMappingRequest;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
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
                .organization(organization)
                .place(place)
                .build();

        sensorType = SensorType.builder()
                .sensorType("testSensorType")
                .build();

        sensorTypeMappings = new SensorTypeMappings(null, sensor, sensorType);

        sensorTypeMappingResponse = sensorTypeMappings.toDto();

        modifySensorTypeMappingRequest = new ModifySensorTypeMappingRequest();

        ReflectionTestUtils.setField(modifySensorTypeMappingRequest, "sensorTypeId", 1);
    }

    @Test
    void sensorTypeMappingCreateSuccessTest() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(sensor));
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.of(sensorType));
        when(sensorTypeMappingRepository.save(any(SensorTypeMappings.class))).thenReturn(sensorTypeMappings);
        SettingFunctionType settingFunctionType = new SettingFunctionType(1L, SettingFunctionType.SETTINGFUNCTIONTYPE.READ_ONLY);
        when(settingFunctionTypeRepository.findById(anyLong())).thenReturn(Optional.of(settingFunctionType));
        when(generalConfigRepository.save(any(GeneralConfig.class))).thenReturn(new GeneralConfig(null, sensorTypeMappings, settingFunctionType, null, LocalDateTime.now()));


        SensorTypeMappingResponse response = sensorTypeMappingService.registerSensorTypeMapping("testSensorSn", 1, 1);

        assertEquals(response.getSensorSn(), sensorTypeMappingResponse.getSensorSn());
        assertEquals(response.getSensorTypeId(), sensorTypeMappingResponse.getSensorTypeId());

        verify(sensorTypeMappingRepository, times(1)).existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        verify(sensorRepository, times(1)).findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt());
        verify(sensorTypeRepository, times(1)).findById(anyInt());
        verify(sensorTypeMappingRepository, times(1)).save(any(SensorTypeMappings.class));
    }

    @Test
    void sensorTypeMappingCreateFailTest() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(true);

        assertThatThrownBy(() -> sensorTypeMappingService.registerSensorTypeMapping("testSensorSn", 1, 1))
                .isInstanceOf(SensorTypeMappingAlreadyExistException.class)
                .hasMessageContaining(SensorTypeMappingAlreadyExistException.MESSAGE);
    }

    @Test
    void sensorTypeMappingCreateFail2Test() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeMappingService.registerSensorTypeMapping("testSensorSn", 1, 1))
                .isInstanceOf(SensorNotExistException.class)
                .hasMessageContaining(SensorNotExistException.MESSAGE);

    }

    @Test
    void sensorTypeMappingCreateFail3Test() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(sensor));
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeMappingService.registerSensorTypeMapping("testSensorSn", 1, 1))
                .isInstanceOf(SensorTypeNotExistException.class)
                .hasMessageContaining(SensorTypeNotExistException.MESSAGE);

    }

    @Test
    void sensorTypeMappingModifySuccessTest() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(sensorTypeMappings));
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.of(sensorType));
        when(sensorTypeMappingRepository.save(any(SensorTypeMappings.class))).thenReturn(sensorTypeMappings);

        SensorTypeMappingResponse response = sensorTypeMappingService.modifySensorTypeMapping("testSensorSn", 1, 1, modifySensorTypeMappingRequest);

        assertEquals(response.getSensorTypeId(), sensorTypeMappingResponse.getSensorTypeId());
        assertEquals(response.getSensorSn(), sensorTypeMappingResponse.getSensorSn());

        verify(sensorTypeMappingRepository, times(1)).existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        verify(sensorTypeMappingRepository, times(1)).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
        verify(sensorTypeRepository, times(1)).findById(anyInt());
        verify(sensorTypeMappingRepository, times(1)).save(any(SensorTypeMappings.class));
    }

    @Test
    void sensorTypeMappingModifyFailTest() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(true);

        assertThatThrownBy(() -> sensorTypeMappingService.modifySensorTypeMapping("testSensorSn", 1, 1, modifySensorTypeMappingRequest))
                .isInstanceOf(SensorTypeMappingAlreadyExistException.class)
                .hasMessageContaining(SensorTypeMappingAlreadyExistException.MESSAGE);
    }

    @Test
    void sensorTypeMappingModifyFail2Test() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeMappingService.modifySensorTypeMapping("testSensorSn", 1, 1, modifySensorTypeMappingRequest))
                .isInstanceOf(SensorTypeMappingNotExistException.class)
                .hasMessageContaining(SensorTypeMappingNotExistException.MESSAGE);
    }

    @Test
    void sensorTypeMappingModifyFail3Test() {
        when(sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(false);
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(sensorTypeMappings));
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeMappingService.modifySensorTypeMapping("testSensorSn", 1, 1, modifySensorTypeMappingRequest))
                .isInstanceOf(SensorTypeNotExistException.class)
                .hasMessageContaining(SensorTypeNotExistException.MESSAGE);
    }

    @Test
    void sensorTypeMappingGetSuccessTest() {
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(sensorTypeMappings));

        SensorTypeMappingResponse response = sensorTypeMappingService.getSensorTypeMapping("testSensorSn", 1,1);

        assertEquals(response.getSensorSn(), sensorTypeMappingResponse.getSensorSn());
        assertEquals(response.getSensorTypeId(), sensorTypeMappingResponse.getSensorTypeId());
        verify(sensorTypeMappingRepository, times(1)).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
    }

    @Test
    void sensorTypeMappingGetFailTest() {
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeMappingService.getSensorTypeMapping("testSensorSn", 1, 1))
                .isInstanceOf(SensorTypeMappingNotExistException.class)
                .hasMessageContaining(SensorTypeMappingNotExistException.MESSAGE);
    }

    @Test
    void sensorTypeMappingDeleteSuccessTest() {
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(sensorTypeMappings));

        sensorTypeMappingService.deleteSensorTypeMapping("testSensorSn", 1, 1);

        verify(sensorTypeMappingRepository, times(1)).findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt());
    }

    @Test
    void sensorTypeMappingDeleteFailTest() {
        when(sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(anyString(), anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeMappingService.deleteSensorTypeMapping("testSensorSn", 1, 1))
                .isInstanceOf(SensorTypeMappingNotExistException.class)
                .hasMessageContaining(SensorTypeMappingNotExistException.MESSAGE);
    }
}
