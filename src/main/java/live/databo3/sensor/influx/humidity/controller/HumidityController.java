package live.databo3.sensor.influx.humidity.controller;

import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.humidity.measurement.HumidityLastData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMaxData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMeanData;
import live.databo3.sensor.influx.humidity.measurement.HumidityMinData;
import live.databo3.sensor.influx.humidity.service.HumidityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/api/sensor/humidity/fields/{fieldType}/branches/{branchName}/places/{placeName}/sensors/{sensorNumber}")
@RequiredArgsConstructor
public class HumidityController {

    private final HumidityService humidityService;

    private static final String ENDPOINT = "humidity";

    @GetMapping({"/last"})
    public ResponseEntity<ResponseSingleData<HumidityLastData>> getLastTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(humidityService.retrieveHumidityLastData(fieldType, branchName,placeName, ENDPOINT, sensorNumber));
    }

    @GetMapping({"/hour/mean"})
    public ResponseEntity<ResponseListData<HumidityMeanData>> getHourlyMeanTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(humidityService.retrieveHumidityHourlyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    @GetMapping({"/day/mean"})
    public ResponseEntity<ResponseListData<HumidityMeanData>> getDailyMeanTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(humidityService.retrieveHumidityDailyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    @GetMapping({"/week/min"})
    public ResponseEntity<ResponseListData<HumidityMinData>> getWeeklyMinTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(humidityService.retrieveHumidityWeeklyMinData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    @GetMapping({"/week/max"})
    public ResponseEntity<ResponseListData<HumidityMaxData>> getWeeklyMaxTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(humidityService.retrieveHumidityWeeklyMaxData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

}
