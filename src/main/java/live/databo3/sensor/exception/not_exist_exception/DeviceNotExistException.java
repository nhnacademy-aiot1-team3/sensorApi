package live.databo3.sensor.exception.not_exist_exception;

public class DeviceNotExistException extends NotExistException {
    public DeviceNotExistException(String deviceSn) {
        super("device", "device-sn: " + deviceSn);
    }
}
