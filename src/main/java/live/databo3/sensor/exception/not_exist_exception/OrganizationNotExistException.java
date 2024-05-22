package live.databo3.sensor.exception.not_exist_exception;

/**
 * organization 이 존재하지 않을 때 던질 에러
 *
 * @author : 강경훈
 * @version : 1.0.0
 */
public class OrganizationNotExistException extends NotExistException {
    public OrganizationNotExistException(Integer organizationId) {
        super("organization", "organizationId-" + organizationId);
    }

    public OrganizationNotExistException(String organizationName) {
        super("organization", "organizationName-" + organizationName);
    }
}
