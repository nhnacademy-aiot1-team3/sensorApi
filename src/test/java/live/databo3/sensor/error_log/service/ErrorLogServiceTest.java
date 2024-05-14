package live.databo3.sensor.error_log.service;

import live.databo3.sensor.error_log.dto.ErrorLogCreateRequestDto;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.error_log.repository.ErrorLogRepository;
import live.databo3.sensor.error_log.service.impl.ErrorLogServiceImpl;
import live.databo3.sensor.exception.not_exist_exception.NotExistErrorLogException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ErrorLogServiceTest {
    @Mock
    private ErrorLogRepository errorLogRepository;
    @Mock
    private SensorRepository sensorRepository;
    @Mock
    private SensorTypeRepository sensorTypeRepository;

    @InjectMocks
    private ErrorLogServiceImpl errorLogService;

    ErrorLog errorLog;
    Sensor sensor;
    SensorType sensorType;
    Organization organization;
    Place place;
    ErrorLogCreateRequestDto errorLogCreateRequestDto;

    @BeforeEach
    void setUp() {
        organization = Organization.builder()
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .placeName("testPlaceName")
                .organization(organization)
                .build();

        sensor = Sensor.builder()
                .sensorSn("testSensorSn")
                .organization(organization)
                .place(place)
                .sensorName("testSensorName")
                .build();

        sensorType = SensorType.builder()
                .sensorType("testSensorType")
                .build();

        errorLog = ErrorLog.builder()
                .sensor(sensor)
                .sensorType(sensorType)
                .value(6.9)
                .errorMsg("testErrorMsg")
                .timestamp(LocalDateTime.now())
                .build();

        errorLogCreateRequestDto = new ErrorLogCreateRequestDto();
        ReflectionTestUtils.setField(errorLogCreateRequestDto, "sensorSn", "testSensorSn");
        ReflectionTestUtils.setField(errorLogCreateRequestDto, "sensorType", "testSensorType");
        ReflectionTestUtils.setField(errorLogCreateRequestDto, "errorMsg", "testErrorMsg");
        ReflectionTestUtils.setField(errorLogCreateRequestDto, "value", 7.4);


    }


    @Test
    @DisplayName("에러 로그 생성 성공 테스트")
    void errorLogCreateTest() {
        when(sensorRepository.findBySensorSn(anyString())).thenReturn(Optional.of(sensor));
        when(sensorTypeRepository.findBySensorType(anyString())).thenReturn(Optional.of(sensorType));

        when(errorLogRepository.save(any(ErrorLog.class))).thenReturn(errorLog);
        errorLogService.createErrorLog(errorLogCreateRequestDto);

        verify(sensorRepository, times(1)).findBySensorSn(anyString());
        verify(sensorTypeRepository, times(1)).findBySensorType(anyString());
        verify(errorLogRepository, times(1)).save(any(ErrorLog.class));
    }

    @Test
    @DisplayName("에러 로그 삭제 실패 테스트")
    void errorLogDeleteFailTest() {
        when(errorLogRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> errorLogService.deleteErrorLog(1L))
                .isInstanceOf(NotExistErrorLogException.class)
                .hasMessageContaining(NotExistErrorLogException.MESSAGE);
    }

    @Test
    @DisplayName("에러 로그 삭제 성공 테스트")
    void errorLogDeleteSuccessTest() {
        when(errorLogRepository.existsById(anyLong())).thenReturn(true);
        errorLogService.deleteErrorLog(1L);

        verify(errorLogRepository, times(1)).existsById(anyLong());
        then(errorLogRepository).should().existsById(1L);
    }

    @Test
    @DisplayName("에러 로그 조회 테스트")
    void errorLogGetTest() {
        when(errorLogRepository.getErrorLogs(anyInt())).thenReturn(Optional.of(List.of(ErrorLogResponseDto.builder()
                .logId(1L)
                .errorMsg("TestMsg")
                .sensor(sensor)
                .sensorType(sensorType)
                .timestamp(LocalDateTime.now())
                .build())));

        errorLogService.getErrorLogs(1);

        verify(errorLogRepository, times(1)).getErrorLogs(anyInt());
        then(errorLogRepository).should().getErrorLogs(anyInt());
    }
}