package live.databo3.sensor.exception.already_exist_exception;

public class SensorTypeMappingAlreadyExistException extends AlreadyExistException {
    public SensorTypeMappingAlreadyExistException(String sensorSn, Integer sensorTypeId) {
        super("sensorTypeMapping", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
