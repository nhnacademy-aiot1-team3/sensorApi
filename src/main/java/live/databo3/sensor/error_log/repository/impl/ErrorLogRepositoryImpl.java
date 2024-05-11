package live.databo3.sensor.error_log.repository.impl;

import com.querydsl.core.types.Projections;
import live.databo3.sensor.error_log.dto.ErrorLogResponseDto;
import live.databo3.sensor.error_log.entity.ErrorLog;
import live.databo3.sensor.error_log.entity.QErrorLog;
import live.databo3.sensor.error_log.repository.ErrorLogRepositoryCustom;
import live.databo3.sensor.organization.entity.QOrganization;
import live.databo3.sensor.sensor.entity.QSensor;
import live.databo3.sensor.sensor_type.entity.QSensorType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class ErrorLogRepositoryImpl extends QuerydslRepositorySupport implements ErrorLogRepositoryCustom {

    private final QErrorLog errorLog = QErrorLog.errorLog;
    private final QOrganization organization = QOrganization.organization;
    private final QSensor sensor = QSensor.sensor;
    private final QSensorType sensorType = QSensorType.sensorType1;

    public ErrorLogRepositoryImpl() {
        super(ErrorLog.class);
    }

    @Override
    public Optional<List<ErrorLogResponseDto>> getErrorLogs(Integer organizationId) {
        return Optional.ofNullable(from(errorLog)
                .select(Projections.constructor(ErrorLogResponseDto.class,
                        errorLog.logId,
                        sensor,
                        sensorType,
                        errorLog.timestamp,
                        errorLog.value,
                        errorLog.errorMsg
                ))
                .leftJoin(sensor).on(errorLog.sensor.eq(sensor))
                .leftJoin(organization).on(sensor.organization.eq(organization))
                .leftJoin(sensorType).on(errorLog.sensorType.eq(sensorType))
                .where(organization.organizationId.eq(organizationId))
                .fetch());
    }
}
