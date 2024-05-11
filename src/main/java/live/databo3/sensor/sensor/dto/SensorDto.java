package live.databo3.sensor.sensor.dto;

public interface SensorDto {
    String getSensorSn();
    String getSensorName();

    PlaceDto getPlace();
    interface PlaceDto {
        Integer getPlaceId();
        String getPlaceName();
    }
}
