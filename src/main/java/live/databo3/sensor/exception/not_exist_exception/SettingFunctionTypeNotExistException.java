package live.databo3.sensor.exception.not_exist_exception;

/**
 * SettingFunctionType 이 존재하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class SettingFunctionTypeNotExistException extends NotExistException {
    public SettingFunctionTypeNotExistException(Long settingFunctionId) {
        super("settingFunctionType", "settingFunctionId-" + settingFunctionId);
    }
}
