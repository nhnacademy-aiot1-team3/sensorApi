package live.databo3.sensor.exception.not_exist_exception;

public class OrganizationNotExistException extends NotExistException {
    public OrganizationNotExistException(Integer organizationId) {
        super("organization", "organizationId-" + organizationId);
    }
}
