package live.databo3.sensor.sensor_type_mappings.service.impl;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.already_exist_exception.SensorTypeMappingAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeMappingNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorTypeNotExistException;
import live.databo3.sensor.member.adaptor.MemberAdaptor;
import live.databo3.sensor.member.dto.MemberOrganizationDto;
import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor_type.entity.SensorType;
import live.databo3.sensor.sensor_type.repository.SensorTypeRepository;
import live.databo3.sensor.sensor_type_mappings.dto.*;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.sensor_type_mappings.service.SensorTypeMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * sensorTypeMapping entity 관련 service
 * CRUD 와 더불어 알맞은 조직의 sensorTypeMapping 을 요청했는지 쿼리를 통해 무결성을 검증하는 역할을 포함한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SensorTypeMappingServiceImpl implements SensorTypeMappingService {
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final MemberAdaptor memberAdaptor;
    private final PlaceRepository placeRepository;

    /**
     * sensorTypeMapping 을 등록한다.
     * 이미 매핑이 존재하는지 확인 한 후 없다면 등록한다.
     * @since 1.0.0
     */
    public SensorTypeMappingResponse registerSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId)) {
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorTypeId);
        }
        Sensor sensor = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));
        SensorType sensorType = sensorTypeRepository.findById(sensorTypeId).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));

        SensorTypeMappings sensorTypeMappings = new SensorTypeMappings(null, sensor, sensorType);
        return sensorTypeMappingRepository.save(sensorTypeMappings).toDto();
    }

    /**
     * sensorTypeMapping 을 수정한다.
     * 조건에 맞는 sensorType 을 조회하여 수정한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public SensorTypeMappingResponse modifySensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId, ModifySensorTypeMappingRequest request) {
        if (sensorTypeMappingRepository.existsBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, request.getSensorTypeId())) {
            throw new SensorTypeMappingAlreadyExistException(sensorSn, sensorTypeId);
        }
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        SensorType sensorType = sensorTypeRepository.findById(request.getSensorTypeId()).orElseThrow(() -> new SensorTypeNotExistException(sensorTypeId));
        sensorTypeMappings.setSensorType(sensorType);
        return sensorTypeMappingRepository.save(sensorTypeMappings).toDto();
    }

    /**
     * sensorTypeMapping 을 조회한다.
     * 조건에 맞는 sensorTypeMapping 을 조회하여 dto 를 반환한다.
     * @since 1.0.0
     */
    public SensorTypeMappingResponse getSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        return sensorTypeMappings.toDto();
    }

    /**
     * sensorTypeMapping 을 삭제한다.
     * 조건에 맞는 sensorTypeMapping 을 조회하여 삭제한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public void deleteSensorTypeMapping(String sensorSn, Integer organizationId, Integer sensorTypeId) {
        SensorTypeMappings sensorTypeMappings = sensorTypeMappingRepository.findBySensor_SensorSnAndSensor_Organization_OrganizationIdAndSensorType_SensorTypeId(sensorSn, organizationId, sensorTypeId).orElseThrow(() -> new SensorTypeMappingNotExistException(sensorSn, sensorTypeId));
        sensorTypeMappingRepository.deleteById(sensorTypeMappings.getRecordNumber());
    }

    public void a(String userId, Integer sensorTypeId) {
        List<MemberOrganizationDto> dtoList = memberAdaptor.getOrganizationsByMember(userId).getBody();
        if (dtoList == null) {
            return;
        }
        List<OrganizationPlaceDto> organizationPlaceDtoList = new ArrayList<>();
        for (MemberOrganizationDto memberOrganizationDto : dtoList) {
            Integer organizationId = memberOrganizationDto.getOrganizationId();
            OrganizationPlaceDto organizationPlaceDto = new OrganizationPlaceDto();
            organizationPlaceDto.setOrganizationId(organizationId);
            organizationPlaceDto.setOrganizationName(memberOrganizationDto.getOrganizationName());

            List<PlaceDto> placeDtoList = placeRepository.findAllByOrganization_OrganizationId(organizationId);
            List<SensorTypeMappings> sensorTypeMappingsList = sensorTypeMappingRepository.findAllBySensorType_SensorTypeIdAndSensor_Organization_OrganizationId(sensorTypeId, organizationId);
            List<PlaceSensorDto> placeSensorDtoList = new ArrayList<>();
            for (PlaceDto placeDto : placeDtoList) {
                PlaceSensorDto placeSensorDto = new PlaceSensorDto();
                Integer placeId = placeDto.getPlaceId();
                placeSensorDto.setPlaceId(placeId);
                placeSensorDto.setPlaceName(placeDto.getPlaceName());
                List<SensorNameIdDto> sensorNameIdDtoList = new ArrayList<>();
                for (SensorTypeMappings sensorTypeMappings : sensorTypeMappingsList) {
                    if (sensorTypeMappings.getSensor().getPlace().getPlaceId().equals(placeId)) {
                        SensorNameIdDto sensorNameIdDto = new SensorNameIdDto();
                        Sensor sensor = sensorTypeMappings.getSensor();
                        sensorNameIdDto.setSensorSn(sensor.getSensorSn());
                        sensorNameIdDto.setSensorName(sensor.getSensorName());

                        sensorNameIdDtoList.add(sensorNameIdDto);
                    }
                }
                placeSensorDto.setSensors(sensorNameIdDtoList);
                placeSensorDtoList.add(placeSensorDto);
            }
            organizationPlaceDto.setPlace(placeSensorDtoList);
            organizationPlaceDtoList.add(organizationPlaceDto);
        }
    }
}
