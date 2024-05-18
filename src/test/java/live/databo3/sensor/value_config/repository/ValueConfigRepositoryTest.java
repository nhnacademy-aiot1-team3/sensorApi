package live.databo3.sensor.value_config.repository;

import live.databo3.sensor.device.entity.Device;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.value_config.dto.ValueConfigResponse;
import live.databo3.sensor.value_config.entity.ValueConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ValueConfigRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ValueConfigRepository valueConfigRepository;

    Organization organization;
    Place place;
    Sensor sensor;
    SensorType sensorType;
    SensorTypeMappings sensorTypeMappings;
    SettingFunctionType settingFunctionType;
    Device device;
    GeneralConfig generalConfig;
    ValueConfig valueConfig;

    ValueConfigResponse valueConfigResponse;

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
        valueConfig = new ValueConfig(null, "firstEntry", "secondEntry", generalConfig);

        valueConfigResponse = valueConfig.toDto();

        entityManager.persist(organization);
        entityManager.persist(place);
        entityManager.persist(sensor);
        entityManager.persist(sensorType);
        entityManager.persist(sensorTypeMappings);
        entityManager.persist(settingFunctionType);
        entityManager.persist(device);
        entityManager.persist(generalConfig);
        entityManager.persist(valueConfig);
    }

    @Test
    void existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdTest() {
        boolean isExist = valueConfigRepository.existsByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeId(sensor.getSensorSn(), organization.getOrganizationId(), sensorType.getSensorTypeId());

        assertTrue(isExist);
    }

    @Test
    void findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumberTest() {
        Optional<ValueConfig> valueConfigOptional = valueConfigRepository.findByGeneralConfig_SensorTypeMappings_Sensor_SensorSnAndGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdAndGeneralConfig_SensorTypeMappings_SensorType_SensorTypeIdAndValueConfigNumber(sensor.getSensorSn(), organization.getOrganizationId(), sensorType.getSensorTypeId(), valueConfig.getValueConfigNumber());

        assertTrue(valueConfigOptional.isPresent());
        assertEquals(valueConfigOptional.get(), valueConfig);
    }

    @Test
    void findAllByGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationIdTest() {
        List<ValueConfig> list = valueConfigRepository.findAllByGeneralConfig_SensorTypeMappings_Sensor_Organization_OrganizationId(organization.getOrganizationId());

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }
}
