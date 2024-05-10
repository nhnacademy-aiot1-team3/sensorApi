package live.databo3.sensor.exception.already_exist_exception;

public class DeviceAlreadyExistException extends AlreadyExistException {
    public DeviceAlreadyExistException(String deviceSn) {
        super("device", "device-sn: " + deviceSn);
    }
}
