package live.databo3.sensor.sensor.dto;

public interface SensorDto {
    String getSensorSn();
    String getSensorName();
    String getSensorPlace();

    OrganizationDto getOrganization();
    interface OrganizationDto {
        Integer getOrganizationId();
        String getOrganizationName();
    }

    SensorTypeDto getSensorType();
    interface SensorTypeDto {
        Integer getSensorTypeId();
        String getSensorType();
    }
}
