package live.databo3.sensor.sensor_type_mappings.dto;

public interface SensorInfoDto {
    Long getRecordNumber();

    SensorTypeDto getSensorType();
    interface SensorTypeDto {
        String getSensorType();
    }

    SensorDto getSensor();
    interface SensorDto {
        String getSensorSn();

        PlaceDto getPlace();
        interface PlaceDto {
            String getPlaceName();
        }

        OrganizationDto getOrganization();
        interface OrganizationDto {
            String getOrganizationName();
        }
    }
}
