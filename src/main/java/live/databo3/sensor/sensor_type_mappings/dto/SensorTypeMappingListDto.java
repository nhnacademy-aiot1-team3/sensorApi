package live.databo3.sensor.sensor_type_mappings.dto;

public interface SensorTypeMappingListDto {
    Long getRecordNumber();
    SensorDto getSensor();
    interface SensorDto{
        String getSensorSn();
        String getSensorName();

        PlaceDto getPlace();

        interface PlaceDto {
            Integer getPlaceId();
            String getPlaceName();
        }
    }
    SensorTypeDto getSensorType();
    interface SensorTypeDto {
        Integer getSensorTypeId();
        String getSensorType();
    }
}
