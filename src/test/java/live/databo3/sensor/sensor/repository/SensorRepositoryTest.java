package live.databo3.sensor.sensor.repository;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.entity.Sensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SensorRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SensorRepository sensorRepository;

    Organization organization;

    Place place;

    Sensor sensor;

    @BeforeEach
    void setup() {
        organization = Organization.builder()
                .controllerSn("testControllerSn")
                .gatewaySn("testGatewaySn")
                .organizationName("testOrganizationName")
                .build();

        place = Place.builder()
                .organization(organization)
                .placeName("testSection")
                .build();

        sensor = Sensor.builder()
                .organization(organization)
                .place(place)
                .sensorSn("testSensorSn")
                .sensorName("testSensorName")
                .build();

        entityManager.persist(organization);
        entityManager.persist(place);
        entityManager.persist(sensor);
    }

    @Test
    void findBySensorSnAndOrganization_OrganizationIdTest() {
        Optional<Sensor> foundSensor = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensor.getSensorSn(), sensor.getOrganization().getOrganizationId());

        assertTrue(foundSensor.isPresent());
        assertEquals(foundSensor.get().getSensorSn(), sensor.getSensorSn());
        assertEquals(foundSensor.get().getSensorName(), sensor.getSensorName());
        assertEquals(foundSensor.get().getOrganization(), sensor.getOrganization());
        assertEquals(foundSensor.get().getPlace(), sensor.getPlace());
    }

    @Test
    void findOneBySensorSnAndOrganization_OrganizationIdTest() {
        Optional<SensorDto> sensorDto = sensorRepository.findOneBySensorSnAndOrganization_OrganizationId(sensor.getSensorSn(), sensor.getOrganization().getOrganizationId());

        assertTrue(sensorDto.isPresent());
        assertEquals(sensorDto.get().getSensorName(), sensor.getSensorName());
        assertEquals(sensorDto.get().getSensorSn(), sensor.getSensorSn());
        assertEquals(sensorDto.get().getPlace().getPlaceId(), sensor.getPlace().getPlaceId());
        assertEquals(sensorDto.get().getPlace().getPlaceName(), sensor.getPlace().getPlaceName());
    }

    @Test
    void findAllByOrganization_OrganizationId() {
        List<SensorDto> list = sensorRepository.findAllByOrganization_OrganizationId(sensor.getOrganization().getOrganizationId());

        assertEquals(1, list.size());
    }

    @Test
    void existsBySensorSnAndOrganization_OrganizationId() {
        boolean isExist = sensorRepository.existsBySensorSnAndOrganization_OrganizationId(sensor.getSensorSn(), sensor.getOrganization().getOrganizationId());

        assertTrue(isExist);
    }

    @Test
    void findBySensorSnTest() {
        Optional<Sensor> foundSensor = sensorRepository.findBySensorSn(sensor.getSensorSn());

        assertTrue(foundSensor.isPresent());
        assertEquals(foundSensor.get(), sensor);
    }
}
