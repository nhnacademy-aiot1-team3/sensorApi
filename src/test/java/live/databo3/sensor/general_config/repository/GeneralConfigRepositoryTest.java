package live.databo3.sensor.general_config.repository;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
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
class GeneralConfigRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    GeneralConfigRepository generalConfigRepository;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorType sensorType;
    SensorTypeMappings sensorTypeMappings;
    SettingFunctionType settingFunctionType;
    Device device;
    GeneralConfig generalConfig;

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
                .place(place)
                .organization(organization)
                .build();

        sensorType = SensorType.builder()
                .sensorType("testSensorType")
                .build();

        sensorTypeMappings = new SensorTypeMappings(null, sensor, sensorType);

        settingFunctionType = new SettingFunctionType(null, SettingFunctionType.SETTINGFUNCTIONTYPE.READ_ONLY);

        device = Device.builder()
                .deviceSn("testDeviceSn")
                .organization(organization)
                .deviceName("testDeviceName")
                .build();

        generalConfig = new GeneralConfig(null, sensorTypeMappings, settingFunctionType, device, null);

        entityManager.persist(organization);
        entityManager.persist(place);
        entityManager.persist(sensor);
        entityManager.persist(sensorType);
        entityManager.persist(sensorTypeMappings);
        entityManager.persist(settingFunctionType);
        entityManager.persist(device);
        entityManager.persist(generalConfig);
    }

    @Test
    void findAllBySensorTypeMappings_Sensor_Organization_OrganizationIdTest() {
        List<GeneralConfig> list = generalConfigRepository.findAllBySensorTypeMappings_Sensor_Organization_OrganizationId(organization.getOrganizationId());

        assertEquals(1, list.size());
        assertEquals(list.get(0), generalConfig);
    }

    @Test
    void existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeIdTest() {
        boolean isExist = generalConfigRepository.existsBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_OrganizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensor.getSensorSn(), organization.getOrganizationId(), sensorType.getSensorTypeId());

        assertTrue(isExist);
    }

    @Test
    void findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeIdTest() {
        Optional<GeneralConfig> generalConfigOptional = generalConfigRepository.findBySensorTypeMappings_Sensor_SensorSnAndSensorTypeMappings_Sensor_Organization_organizationIdAndSensorTypeMappings_SensorType_SensorTypeId(sensor.getSensorSn(), organization.getOrganizationId(), sensorType.getSensorTypeId());

        assertTrue(generalConfigOptional.isPresent());
        assertEquals(generalConfigOptional.get(), generalConfig);
    }
}
