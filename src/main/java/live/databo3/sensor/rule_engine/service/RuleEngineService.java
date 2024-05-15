package live.databo3.sensor.rule_engine.service;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleEngineService {
    private final PlaceRepository placeRepository;
    private final OrganizationRepository organizationRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorTypeMappingRepository sensorTypeMappingRepository;


    @Transactional
    public void registerSensor(String organizationName, String placeName, String sensorSn, String sensorTypeName) {
        Optional<Place> placeOptional = placeRepository.findByPlaceNameAndOrganization_OrganizationName(placeName, organizationName);
        Organization organization = organizationRepository.findByOrganizationName(organizationName).orElseThrow(() -> new OrganizationNotExistException(organizationName));
        Place place = placeOptional.orElseGet(() -> placeRepository.save(new Place(null, placeName, organization)));
        Sensor sensor = sensorRepository.save(new Sensor(sensorSn, null, place, organization));
        SensorType sensorType = sensorTypeRepository.findBySensorType(sensorTypeName).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeName));

        sensorTypeMappingRepository.save(new SensorTypeMappings(null, sensor, sensorType));
        clearRedis(organization.getOrganizationId());
    }

    @ClearRedis
    public void clearRedis(Integer organizationId) {
    }
}
