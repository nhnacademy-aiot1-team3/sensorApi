package live.databo3.sensor.sensorTypeMappings.repository;

import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
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
class SensorTypeMappingRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SensorTypeMappingRepository sensorTypeMappingRepository;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorType sensorType;
    SensorTypeMappings sensorTypeMappings;

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

        entityManager.persist(organization);
        entityManager.persist(place);
        entityManager.persist(sensor);
        entityManager.persist(sensorType);
        entityManager.persist(sensorTypeMappings);
    }

    @Test
    void findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeIdTest() {
        Optional<SensorTypeMappings> foundSensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensor.getSensorSn(), organization.getOrganizationId(), sensorType.getSensorTypeId());

        assertTrue(foundSensorTypeMappings.isPresent());
        assertEquals(foundSensorTypeMappings.get().getSensor(), sensor);
        assertEquals(foundSensorTypeMappings.get().getSensorType(), sensorType);
    }

    @Test
    void existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeIdTest() {
        boolean isExist = sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensor.getSensorSn(), organization.getOrganizationId(), sensorType.getSensorTypeId());

        assertTrue(isExist);
    }

    @Test
    void findAllBySensorType_SensorTypeIdAndSensor_Organization_OrganizationIdTest() {
        List<SensorTypeMappings> list = sensorTypeMappingRepository.findAllBySensorType_SensorTypeIdAndSensor_Organization_OrganizationId(sensorType.getSensorTypeId(), organization.getOrganizationId());

        assertEquals(1, list.size());
        assertEquals(list.get(0), sensorTypeMappings);
    }
}
