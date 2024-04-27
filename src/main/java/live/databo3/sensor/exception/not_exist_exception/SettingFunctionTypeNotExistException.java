package live.databo3.sensor.exception.not_exist_exception;

public class SettingFunctionTypeNotExistException extends NotExistException {
    public SettingFunctionTypeNotExistException(Long settingFunctionId) {
        super("settingFunctionType", "settingFunctionId-" + settingFunctionId);
    }
}
