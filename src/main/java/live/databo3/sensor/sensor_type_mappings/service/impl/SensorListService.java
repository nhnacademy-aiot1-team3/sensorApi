package live.databo3.sensor.sensor_type_mappings.service.impl;

import live.databo3.sensor.member.adaptor.MemberAdaptor;
import live.databo3.sensor.member.dto.MemberOrganizationDto;
import live.databo3.sensor.place.dto.PlaceDto;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor_type_mappings.dto.OrganizationPlaceDto;
import live.databo3.sensor.sensor_type_mappings.dto.PlaceSensorDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorListForRedisDto;
import live.databo3.sensor.sensor_type_mappings.dto.SensorNameIdDto;
import live.databo3.sensor.sensor_type_mappings.entity.SensorTypeMappings;
import live.databo3.sensor.sensor_type_mappings.repository.SensorTypeMappingRepository;
import live.databo3.sensor.util.ExtractHeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 센서리스트들을 반환하기 위한 서비스
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SensorListService {
    private final SensorTypeMappingRepository sensorTypeMappingRepository;
    private final PlaceRepository placeRepository;
    private final MemberAdaptor memberAdaptor;
    private final ExtractHeaderUtil extractHeaderUtil;

    public List<SensorListForRedisDto> getSensorListByOrganizationName(String organizationName) {
        List<SensorTypeMappings> mappings = sensorTypeMappingRepository.findAllBySensor_Organization_OrganizationName(organizationName);
        HashMap<String, List<String>> map = new HashMap<>();
        for (SensorTypeMappings mapping : mappings) {
            String sensorSn = mapping.getSensor().getSensorSn();
            String sensorType = mapping.getSensorType().getSensorType();
            if (!map.containsKey(sensorSn)) {
                List<String> list = new ArrayList<>();
                list.add(sensorType);
                map.put(sensorSn, list);
            } else {
                map.get(sensorSn).add(sensorType);
            }
        }
        List<SensorListForRedisDto> sensorList = new ArrayList<>();
        Set<Map.Entry<String, List<String>>> entrySet = map.entrySet();

        for (Map.Entry<String, List<String>> entry : entrySet) {
            sensorList.add(new SensorListForRedisDto(entry.getKey(), entry.getValue()));
        }
        return sensorList;
    }

    public List<OrganizationPlaceDto> getSensorListBySensorType (Integer sensorTypeId) {
        String userId = extractHeaderUtil.extractHeader("X-USER-ID");
        return getOrganizationPlaces(userId, sensorTypeId);
    }

    public List<OrganizationPlaceDto> getOrganizationPlaces(String userId, Integer sensorTypeId) {
        List<MemberOrganizationDto> dtoList = memberAdaptor.getOrganizationsByMember(userId).getBody();
        if (dtoList == null) {
            return Collections.emptyList();
        }

        return dtoList.stream()
                .map(memberOrganizationDto -> buildOrganizationPlaceDto(memberOrganizationDto, sensorTypeId))
                .collect(Collectors.toList());
    }

    private OrganizationPlaceDto buildOrganizationPlaceDto(MemberOrganizationDto memberOrganizationDto, Integer sensorTypeId) {
        Integer organizationId = memberOrganizationDto.getOrganizationId();
        OrganizationPlaceDto organizationPlaceDto = new OrganizationPlaceDto();
        organizationPlaceDto.setOrganizationId(organizationId);
        organizationPlaceDto.setOrganizationName(memberOrganizationDto.getOrganizationName());

        List<PlaceDto> placeDtoList = placeRepository.findAllByOrganization_OrganizationId(organizationId);
        List<SensorTypeMappings> sensorTypeMappingsList = sensorTypeMappingRepository.findAllBySensorType_SensorTypeIdAndSensor_Organization_OrganizationId(sensorTypeId, organizationId);
        List<PlaceSensorDto> placeSensorDtoList = buildPlaceSensorDtoList(placeDtoList, sensorTypeMappingsList);

        organizationPlaceDto.setPlace(placeSensorDtoList);
        return organizationPlaceDto;
    }

    private List<PlaceSensorDto> buildPlaceSensorDtoList(List<PlaceDto> placeDtoList, List<SensorTypeMappings> sensorTypeMappingsList) {
        return placeDtoList.stream()
                .map(placeDto -> buildPlaceSensorDto(placeDto, sensorTypeMappingsList))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PlaceSensorDto buildPlaceSensorDto(PlaceDto placeDto, List<SensorTypeMappings> sensorTypeMappingsList) {
        Integer placeId = placeDto.getPlaceId();
        PlaceSensorDto placeSensorDto = new PlaceSensorDto();
        placeSensorDto.setPlaceId(placeId);
        placeSensorDto.setPlaceName(placeDto.getPlaceName());

        List<SensorNameIdDto> sensorNameIdDtoList = sensorTypeMappingsList.stream()
                .filter(sensorTypeMappings -> sensorTypeMappings.getSensor().getPlace().getPlaceId().equals(placeId))
                .map(sensorTypeMappings -> {
                    Sensor sensor = sensorTypeMappings.getSensor();
                    SensorNameIdDto sensorNameIdDto = new SensorNameIdDto();
                    sensorNameIdDto.setSensorSn(sensor.getSensorSn());
                    sensorNameIdDto.setSensorName(sensor.getSensorName());
                    return sensorNameIdDto;
                })
                .collect(Collectors.toList());

        placeSensorDto.setSensors(sensorNameIdDtoList);
        return placeSensorDto;
    }
}
