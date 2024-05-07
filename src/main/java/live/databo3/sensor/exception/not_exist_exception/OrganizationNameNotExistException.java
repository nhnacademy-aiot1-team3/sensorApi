package live.databo3.sensor.exception.not_exist_exception;

public class OrganizationNameNotExistException extends NotExistException {
    public OrganizationNameNotExistException(String organizationName) {
        super("organization", "organizationName-" + organizationName);
    }
}
