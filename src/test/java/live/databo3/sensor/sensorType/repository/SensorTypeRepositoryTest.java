package live.databo3.sensor.sensorType.repository;

import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class SensorTypeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    SensorTypeRepository sensorTypeRepository;

    SensorType sensorType;

    @BeforeEach
    void setup() {
        sensorType = SensorType.builder()
                .sensorType("testSensorType")
                .build();

        entityManager.persist(sensorType);
    }

    @Test
    void findBySensorTypeTest() {
        Optional<SensorType> foundSensorType = sensorTypeRepository.findBySensorType(sensorType.getSensorType());

        assertTrue(foundSensorType.isPresent());
        assertEquals(foundSensorType.get(), sensorType);
    }
}
