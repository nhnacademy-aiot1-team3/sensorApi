package live.databo3.sensor.rule_engine.service;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.already_exist_exception.SensorTypeMappingAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SettingFunctionTypeNotExistException;
import live.databo3.sensor.general_config.entity.GeneralConfig;
import live.databo3.sensor.general_config.repository.GeneralConfigRepository;
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
import live.databo3.sensor.setting_function_type.entity.SettingFunctionType;
import live.databo3.sensor.setting_function_type.repository.SettingFunctionTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * ruleEngine 에서 보낸 동적으로 센서 추가하는 요청을 받는 api 입니다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleEngineService {
    private final PlaceRepository placeRepository;
    private final OrganizationRepository organizationRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final GeneralConfigRepository generalConfigRepository;
    private final SettingFunctionTypeRepository settingFunctionTypeRepository;

    /**
     * 센서의 정보를 받아 장소가 이미 존재하지 않는다면 장소를 새로 만들고, 센서가 존재하지 않는다면 센서를 새로 만들고,
     * 센서에 해당 센서 타입이 존재하지 않는다면 센서에 해당 센서타입을 매핑 시킵니다.
     * 센서가 성공적으로 생성되었다면, 기본으로 설정값이 READ_ONLY 이고 연결된 device 가 null 인 일반 설정을 생성합니다.
     * @since 1.0.0
     */
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
        }

        Optional<Sensor> sensorOptional = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organization.getOrganizationId());
        Sensor sensor;
        if (sensorOptional.isPresent()) {
            sensor = sensorOptional.get();
        } else {
            sensor = sensorRepository.saveAndFlush(new Sensor(sensorSn, null, place, organization));
        }

        SensorType sensorType = sensorTypeRepository.findBySensorType(sensorTypeName).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeName));

        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organization.getOrganizationId(), sensorType.getSensorTypeId())){
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorType.getSensorTypeId());
        }
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.saveAndFlush(new SensorTypeMappings(null, sensor, sensorType));
        SettingFunctionType settingFunctionType = settingFunctionTypeRepository.findById(1L).orElseThrow(() -> new SettingFunctionTypeNotExistException(1L));

        generalConfigRepository.save(new GeneralConfig(null, sensorTypeMappings, settingFunctionType, null, LocalDateTime.now()));
    }
}
