package live.databo3.sensor.sensor.service;

import live.databo3.sensor.exception.already_exist_exception.SensorAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.PlaceNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.SensorResponse;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor.service.impl.SensorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private SensorServiceImpl sensorService;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorResponse sensorResponse;
    RegisterSensorRequest registerSensorRequest;
    ModifySensorRequest modifySensorRequest;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .placeId(1)
                .placeName("testSection")
                .build();

        sensor = Sensor.builder()
                .sensorSn("testSensorSn")
                .sensorName("testSensorName")
                .organization(organization)
                .place(place)
                .build();

        registerSensorRequest = new RegisterSensorRequest();
        ReflectionTestUtils.setField(registerSensorRequest, "sensorSn", "testSensorSn");
        ReflectionTestUtils.setField(registerSensorRequest, "sensorName", "testSensorName");
        ReflectionTestUtils.setField(registerSensorRequest, "placeId", 1);

        modifySensorRequest = new ModifySensorRequest();
        ReflectionTestUtils.setField(modifySensorRequest, "sensorName", "testSensorName");
        ReflectionTestUtils.setField(modifySensorRequest, "placeId", 1);

        sensorResponse = sensor.toDto();
    }

    @Test
    void sensorCreateSuccessTest() {
        when(sensorRepository.existsById(anyString())).thenReturn(false);
        when(organizationRepository.findById(anyInt())).thenReturn(Optional.of(organization));
        when(placeRepository.findById(anyInt())).thenReturn(Optional.of(place));

        SensorResponse response = sensorService.registerSensor(1, registerSensorRequest);
        assertEquals(response.getSensorSn(), sensorResponse.getSensorSn());
        assertEquals(response.getSensorName(), sensorResponse.getSensorName());
        assertEquals(response.getPlaceId(), sensorResponse.getPlaceId());
        assertEquals(response.getPlaceName(), sensorResponse.getPlaceName());

        verify(sensorRepository, times(1)).existsById(anyString());
        verify(organizationRepository, times(1)).findById(anyInt());
        verify(placeRepository, times(1)).findById(anyInt());
    }

    @Test
    void sensorCreateFailTest() {
        when(sensorRepository.existsById(anyString())).thenReturn(true);

        assertThatThrownBy(() -> sensorService.registerSensor(1, registerSensorRequest))
                .isInstanceOf(SensorAlreadyExistException.class)
                .hasMessageContaining(SensorAlreadyExistException.MESSAGE);
    }

    @Test
    void sensorModifySuccessTest() {
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(sensor));
        when(placeRepository.findByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt())).thenReturn(Optional.of(place));


        SensorResponse response = sensorService.modifySensor(1, "testSensorSn", modifySensorRequest);
        assertEquals(response.getSensorName(), sensor.getSensorName());
        assertEquals(response.getSensorSn(), sensor.getSensorSn());
        assertEquals(response.getPlaceId(), sensor.getPlace().getPlaceId());
        assertEquals(response.getPlaceName(), sensor.getPlace().getPlaceName());
    }

    @Test
    void sensorModifyFailTest() {
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorService.modifySensor(1, "testSensorSn", modifySensorRequest))
                .isInstanceOf(SensorNotExistException.class)
                .hasMessageContaining(SensorNotExistException.MESSAGE);
    }

    @Test
    void sensorModifyFailTest2() {
        when(sensorRepository.findBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(sensor));
        when(placeRepository.findByPlaceIdAndOrganization_OrganizationId(anyInt(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorService.modifySensor(1, "testSensorSn", modifySensorRequest))
                .isInstanceOf(PlaceNotExistException.class)
                .hasMessageContaining(PlaceNotExistException.MESSAGE);
    }

    @Test
    void sensorGetSuccessTest() {
        SensorDto sensorDto = new SensorDto() {
            @Override
            public String getSensorSn() {
                return null;
            }
            @Override
            public String getSensorName() {
                return null;
            }
            @Override
            public PlaceDto getPlace() {
                return null;
            }
        };
        when(sensorRepository.findOneBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.of(sensorDto));

        sensorService.getSensor(1, "testSensorSn");
        verify(sensorRepository, times(1)).findOneBySensorSnAndOrganization_OrganizationId(anyString(), anyInt());
    }

    @Test
    void sensorGetFailTest() {
        when(sensorRepository.findOneBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sensorService.getSensor(1, "testSensorSn"))
                .isInstanceOf(SensorNotExistException.class)
                .hasMessageContaining(SensorNotExistException.MESSAGE);
    }

    @Test
    void sensorDeleteSuccessTest() {
        when(sensorRepository.existsBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(true);

        sensorService.deleteSensor(1, sensor.getSensorSn());

        verify(sensorRepository, times(1)).existsBySensorSnAndOrganization_OrganizationId(anyString(), anyInt());
        then(sensorRepository).should().existsBySensorSnAndOrganization_OrganizationId(sensor.getSensorSn(), 1);
    }

    @Test
    void sensorDeleteFailTest() {
        when(sensorRepository.existsBySensorSnAndOrganization_OrganizationId(anyString(), anyInt())).thenReturn(false);

        assertThatThrownBy(() -> sensorService.deleteSensor(1, sensor.getSensorSn()))
                .isInstanceOf(SensorNotExistException.class)
                .hasMessageContaining(SensorNotExistException.MESSAGE);
    }

    @Test
    void sensorListGetTest() {
        when(sensorRepository.findAllByOrganization_OrganizationId(anyInt())).thenReturn(new ArrayList<SensorDto>());

        sensorService.getSensors(1);
        verify(sensorRepository, times(1)).findAllByOrganization_OrganizationId(anyInt());
    }
}
