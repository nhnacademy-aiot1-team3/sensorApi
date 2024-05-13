package live.databo3.sensor.influx.temperature.controller;

import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureLastData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMaxData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMeanData;
import live.databo3.sensor.influx.temperature.measurement.TemperatureMinData;
import live.databo3.sensor.influx.temperature.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// endpoint도 pathVariable로 바꿔서 할 수 있나? 고민좀
@Slf4j
@RestController
@RequestMapping("/api/data/temperature/fields/{fieldType}/branches/{branchName}/places/{placeName}/sensors/{sensorNumber}")
@RequiredArgsConstructor
public class TemperatureController {

    private final TemperatureService temperatureService;
    private static final String ENDPOINT = "temperature";

    @GetMapping({"/last"})
    public ResponseEntity<ResponseSingleData<TemperatureLastData>> getLastTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {
        log.info("field : {}, branch : {}, place : {}, sensor : {}", fieldType, branchName, placeName, sensorNumber);

        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveLastData(fieldType, branchName,placeName, ENDPOINT, sensorNumber));
    }

    @GetMapping({"/hour/mean"})

    public ResponseEntity<ResponseListData<TemperatureMeanData>> getHourlyMeanTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveHourlyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber));
    }

    // /day/mean
    @GetMapping({"/day/mean"})
    public ResponseEntity<ResponseListData<TemperatureMeanData>> getDailyMeanTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveDailyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber));
    }

    // /week/min
    @GetMapping({"/week/min"})
    public ResponseEntity<ResponseListData<TemperatureMinData>> getWeeklyMinTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveWeeklyMinData(fieldType, branchName, placeName, ENDPOINT, sensorNumber));
    }

    // /week/max
    @GetMapping({"/week/max"})
    public ResponseEntity<ResponseListData<TemperatureMaxData>> getWeeklyMaxTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveWeeklyMaxData(fieldType, branchName, placeName, ENDPOINT, sensorNumber));
    }

}
