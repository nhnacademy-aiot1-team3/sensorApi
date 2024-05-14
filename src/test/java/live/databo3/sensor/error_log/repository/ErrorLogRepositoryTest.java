package live.databo3.sensor.error_log.repository;

import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ErrorLogRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ErrorLogRepository errorLogRepository;

    ErrorLog errorLog;
    Sensor sensor;
    SensorType sensorType;
    Organization organization;
    Place place;

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

        entityManager.persist(organization);
        entityManager.persist(place);
        entityManager.persist(sensor);
        entityManager.persist(sensorType);
    }

    @Test
    @DisplayName("에러 로그 save 테스트")
    void ErrorLogSaveTest() {
        ErrorLog persist = entityManager.persist(errorLog);
        Optional<ErrorLog> errorLog = errorLogRepository.findById(persist.getLogId());

        assertThat(errorLog).isPresent();
        assertThat(errorLog.get().getLogId()).isEqualTo(persist.getLogId());
        assertThat(errorLog.get().getErrorMsg()).isEqualTo(persist.getErrorMsg());
        assertThat(errorLog.get().getTimestamp()).isEqualTo(persist.getTimestamp());
        assertThat(errorLog.get().getSensor().getSensorSn()).isEqualTo(persist.getSensor().getSensorSn());
        assertThat(errorLog.get().getSensorType().getSensorType()).isEqualTo(persist.getSensorType().getSensorType());
        assertThat(errorLog.get().getValue()).isEqualTo(persist.getValue());
        System.out.println(persist);
    }

    @Test
    @DisplayName("에러 로그 전체조회 테스트")
    void findErrorLogsTest() {
        ErrorLog persist = entityManager.persist(errorLog);
        Optional<List<ErrorLogResponseDto>> result = errorLogRepository.getErrorLogs(organization.getOrganizationId());
        List<ErrorLogResponseDto> content = result.get();

        assertThat(result).isPresent();
        assertThat(content.get(0).getErrorMsg()).isEqualTo(persist.getErrorMsg());
        assertThat(content.get(0).getTimestamp()).isEqualTo(persist.getTimestamp());
        assertThat(content.get(0).getSensor().getSensorSn()).isEqualTo(persist.getSensor().getSensorSn());
        assertThat(content.get(0).getSensorType().getSensorType()).isEqualTo(persist.getSensorType().getSensorType());
        assertThat(content.get(0).getValue()).isEqualTo(persist.getValue());
    }

}