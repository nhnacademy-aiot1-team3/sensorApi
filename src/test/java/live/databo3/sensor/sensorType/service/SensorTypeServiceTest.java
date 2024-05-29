package live.databo3.sensor.sensorType.service;

import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.sensor_type.dto.request.ModifySensorTypeRequest;
import live.databo3.sensor.sensor_type.dto.request.RegisterSensorTypeRequest;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type.service.impl.SensorTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorTypeServiceTest {
    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @InjectMocks
    private SensorTypeServiceImpl sensorTypeService;

    SensorType sensorType;
    RegisterSensorTypeRequest registerSensorTypeRequest;
    ModifySensorTypeRequest modifySensorTypeRequest;

    @BeforeEach
    void setup() {
        sensorType = SensorType.builder()
                .sensorType("testSensorType")
                .build();

        registerSensorTypeRequest = new RegisterSensorTypeRequest();
        ReflectionTestUtils.setField(registerSensorTypeRequest, "sensorType", "testSensorType");

        modifySensorTypeRequest = new ModifySensorTypeRequest();
        ReflectionTestUtils.setField(modifySensorTypeRequest, "sensorType", "testSensorType");
    }

    @Test
    void sensorTypeCreateSuccessTest() {
        when(sensorTypeRepository.save(any(SensorType.class))).thenReturn(sensorType);
        SensorType registeredSensorType = sensorTypeService.registerSensorType(registerSensorTypeRequest);
        assertEquals(registeredSensorType.getSensorType(), sensorType.getSensorType());
        verify(sensorTypeRepository, times(1)).save(any(SensorType.class));
    }

    @Test
    void sensorTypeModifySuccessTest() {
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.of(sensorType));
        SensorType modifiedSensorType = sensorTypeService.modifySensorType(1, modifySensorTypeRequest);
        assertEquals(modifiedSensorType.getSensorType(), sensorType.getSensorType());
        verify(sensorTypeRepository, times(1)).findById(anyInt());
    }

    @Test
    void sensorTypeModifyFailTest() {
        when(sensorTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorTypeService.modifySensorType(1, modifySensorTypeRequest))
                .isInstanceOf(SensorTypeNotExistException.class)
                .hasMessageContaining(SensorTypeNotExistException.MESSAGE);
    }

    @Test
    void sensorTypeListGetTest() {
        sensorTypeService.getSensorTypes();

        verify(sensorTypeRepository, times(1)).findAll();
    }

    @Test
    void sensorTypeDeleteSuccessTest() {
        when(sensorTypeRepository.existsById(anyInt())).thenReturn(true);

        sensorTypeService.deleteSensorTypes(1);

        verify(sensorTypeRepository, times(1)).existsById(anyInt());
    }

    @Test
    void sensorTypeDeleteFailTest() {
        when(sensorTypeRepository.existsById(anyInt())).thenReturn(false);

        assertThatThrownBy(() -> sensorTypeService.deleteSensorTypes(1))
                .isInstanceOf(SensorTypeNotExistException.class)
                .hasMessageContaining(SensorTypeNotExistException.MESSAGE);
    }
}
