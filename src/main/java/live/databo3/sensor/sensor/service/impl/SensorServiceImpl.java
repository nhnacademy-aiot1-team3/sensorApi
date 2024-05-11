package live.databo3.sensor.sensor.service.impl;

import live.databo3.sensor.annotations.ClearRedis;
import live.databo3.sensor.exception.already_exist_exception.SensorAlreadyExistException;
import live.databo3.sensor.exception.not_exist_exception.OrganizationNotExistException;
import live.databo3.sensor.exception.not_exist_exception.PlaceNotExistException;
import live.databo3.sensor.exception.not_exist_exception.SensorNotExistException;
import live.databo3.sensor.member.adaptor.MemberAdaptor;
import live.databo3.sensor.member.dto.MemberOrganizationDto;
import live.databo3.sensor.organization.entity.Organization;
import live.databo3.sensor.organization.repository.OrganizationRepository;
import live.databo3.sensor.place.entity.Place;
import live.databo3.sensor.place.repository.PlaceRepository;
import live.databo3.sensor.sensor.dto.SensorDto;
import live.databo3.sensor.sensor.dto.request.ModifySensorRequest;
import live.databo3.sensor.sensor.dto.request.RegisterSensorRequest;
import live.databo3.sensor.sensor.dto.SensorResponse;
import live.databo3.sensor.sensor.entity.Sensor;
import live.databo3.sensor.sensor.repository.SensorRepository;
import live.databo3.sensor.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * sensor entity 관련 service
 * CRUD 와 더불어 알맞은 조직의 sensor 를 요청했는지 쿼리를 통해 무결성을 검증하는 역할을 포함한다.
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    private final OrganizationRepository organizationRepository;
    private final PlaceRepository placeRepository;
    private final MemberAdaptor memberAdaptor;

    /**
     * 이미 존재하는 sensor 인지 확인 한 후에 없다면 request 의 body 를 통해 생성한다.
     * @since 1.0.0
     */
    public SensorResponse registerSensor(Integer organizationId, RegisterSensorRequest request) {
        String sensorSn = request.getSensorSn();
        if (sensorRepository.existsById(sensorSn)) {
            throw new SensorAlreadyExistException(sensorSn);
        }

        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new OrganizationNotExistException(organizationId));

        Integer placeId = request.getPlaceId();
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotExistException(placeId));

        Sensor sensor = new Sensor(sensorSn, request.getSensorName(), place, organization);

        sensorRepository.save(sensor);

        return sensor.toDto();
    }

    /**
     * sensor 를 조회하여 수정한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public SensorResponse modifySensor(Integer organizationId, String sensorSn, ModifySensorRequest request) {
        Sensor sensor = sensorRepository.findBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));
        Integer placeId = request.getPlaceId();
        Place place = placeRepository.findByPlaceIdAndOrganization_OrganizationId(placeId, organizationId).orElseThrow(() -> new PlaceNotExistException(placeId));
        sensor.setSensorName(request.getSensorName());
        sensor.setPlace(place);

        return sensor.toDto();
    }

    /**
     * 특정 조직에 속하는 device 들의 list 를 반환한다.
     * @since 1.0.0
     */
    public List<SensorDto> getSensors(Integer organizationId) {
        return sensorRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    /**
     * sensor 정보 일부를 반환한다.
     * @since 1.0.0
     */
    public SensorDto getSensor(Integer organizationId, String sensorSn) {
        return sensorRepository.findOneBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId).orElseThrow(() -> new SensorNotExistException(sensorSn));
    }

    /**
     * sensor 가 존재하는지 체크한 후 존재한다면 제거한다.
     * @since 1.0.0
     */
    @Transactional
    @ClearRedis
    public void deleteSensor(Integer organizationId, String sensorSn) {
        if (!sensorRepository.existsBySensorSnAndOrganization_OrganizationId(sensorSn, organizationId)) {
            throw new SensorNotExistException(sensorSn);
        }
        sensorRepository.deleteById(sensorSn);
    }
}
