package live.databo3.sensor.exception.not_exist_exception;

public class SensorTypeMappingNotExistException extends NotExistException {
    public SensorTypeMappingNotExistException(String sensorSn, Integer sensorTypeId) {
        super("sensorTypeMapping", "sensorSn-" + sensorSn + ", sensorTypeId-" + sensorTypeId);
    }
}
