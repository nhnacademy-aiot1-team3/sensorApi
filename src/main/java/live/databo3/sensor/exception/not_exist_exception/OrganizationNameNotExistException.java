package live.databo3.sensor.exception.not_exist_exception;

/**
 * 조직 이름이 존재하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class OrganizationNameNotExistException extends NotExistException {
    public OrganizationNameNotExistException(String organizationName) {
        super("organization", "organizationName-" + organizationName);
    }
}
