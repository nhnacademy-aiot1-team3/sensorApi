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
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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

        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveTemperatureLastData(fieldType, branchName,placeName, ENDPOINT, sensorNumber));
    }

    @GetMapping({"/hour/mean"})
    public ResponseEntity<ResponseListData<TemperatureMeanData>> getHourlyMeanTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveTemperatureHourlyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    // /day/mean
    @GetMapping({"/day/mean"})
    public ResponseEntity<ResponseListData<TemperatureMeanData>> getDailyMeanTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveTemperatureDailyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    // /week/min
    @GetMapping({"/week/min"})
    public ResponseEntity<ResponseListData<TemperatureMinData>> getWeeklyMinTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveTemperatureWeeklyMinData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    // /week/max
    @GetMapping({"/week/max"})
    public ResponseEntity<ResponseListData<TemperatureMaxData>> getWeeklyMaxTemperatureData(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(temperatureService.retrieveTemperatureWeeklyMaxData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

}
