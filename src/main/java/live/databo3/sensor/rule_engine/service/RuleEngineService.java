package live.databo3.sensor.rule_engine.service;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.already_exist_exception.SensorTypeMappingAlreadyExistException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleEngineService {
    private final PlaceRepository placeRepository;
    private final OrganizationRepository organizationRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorTypeMappingRepository sensorTypeMappingRepository;


    @Transactional
    @ClearRedis
    public void registerSensor(String organizationName, String placeName, String sensorSn, String sensorTypeName) {
        Organization organization = organizationRepository.findByOrganizationName(organizationName).orElseThrow(() -> new OrganizationNotExistException(organizationName));

        Optional<Place> placeOptional = placeRepository.findByPlaceNameAndOrganization_OrganizationName(placeName, organizationName);
        Place place;
        if (placeOptional.isPresent()) {
            place = placeOptional.get();
        } else {
            place = placeRepository.saveAndFlush(new Place(null, placeName, organization));
            log.debug("place registered: " + place.getPlaceName() + "-" + organizationName);
        }

        Optional<Sensor> sensorOptional = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organization.getOrganizationId());
        Sensor sensor;
        if (sensorOptional.isPresent()) {
            sensor = sensorOptional.get();
        } else {
            sensor = sensorRepository.saveAndFlush(new Sensor(sensorSn, null, place, organization));
            log.debug("sensor registered: " + sensorSn + "-" + placeName);
        }

        SensorType sensorType = sensorTypeRepository.findBySensorType(sensorTypeName).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeName));

        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organization.getOrganizationId(), sensorType.getSensorTypeId())){
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorType.getSensorTypeId());
        }
        sensorTypeMappingRepository.saveAndFlush(new SensorTypeMappings(null, sensor, sensorType));
    }
}
