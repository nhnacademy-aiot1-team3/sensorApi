package live.databo3.sensor.error_log.controller;

import com.netflix.discovery.converters.Auto;
import live.databo3.sensor.error_log.dto.ErrorLogCreateRequestDto;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.error_log.service.ErrorLogService;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ErrorLogController.class)
class ErrorLogControllerTest {

    @MockBean
    private ErrorLogService errorLogService;

    @Autowired
    MockMvc mockMvc;

    ErrorLog errorLog;
    Sensor sensor;
    SensorType sensorType;
    Organization organization;
    Place place;
    ErrorLogCreateRequestDto errorLogCreateRequestDto;
    ErrorLogResponseDto errorLogResponseDto;

    @BeforeEach
    void setUp() {
        organization = Organization.builder()
                .organizationId(1)
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .placeId(1)
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
                .sensorTypeId(1)
                .sensorType("testSensorType")
                .build();

        errorLog = ErrorLog.builder()
                .logId(1L)
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

        errorLogResponseDto = ErrorLogResponseDto.builder()
                .logId(errorLog.getLogId())
                .timestamp(errorLog.getTimestamp())
                .sensorType(errorLog.getSensorType())
                .sensor(errorLog.getSensor())
                .errorMsg(errorLog.getErrorMsg())
                .value(errorLog.getValue())
                .build();

    }


    @Test
    @DisplayName("에러 로그 생성 테스트")
    void createErrorLog() throws Exception {
        given(errorLogService.createErrorLog(any())).willReturn(errorLogResponseDto);

    }
}