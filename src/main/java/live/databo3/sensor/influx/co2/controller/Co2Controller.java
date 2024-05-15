package live.databo3.sensor.influx.co2.controller;

import live.databo3.sensor.influx.co2.measurement.Co2LastData;
import live.databo3.sensor.influx.co2.measurement.Co2MeanData;
import live.databo3.sensor.influx.co2.service.Co2Service;
import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/api/data/co2/fields/{fieldType}/branches/{branchName}/places/{placeName}/sensors/{sensorNumber}")
@RequiredArgsConstructor
public class Co2Controller {

    private final Co2Service co2Service;

    private static final String ENDPOINT = "co2";

    @GetMapping("/last")
    public ResponseEntity<ResponseSingleData<Co2LastData>> getLastCo2Data(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(co2Service.retrieveCo2LastData(fieldType, branchName, placeName, ENDPOINT, sensorNumber));
    }

    @GetMapping("/hour/mean")
    public ResponseEntity<ResponseListData<Co2MeanData>> getHourlyMeanCo2Data(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(co2Service.retrieveCo2HourlyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    @GetMapping("/day/mean")
    public ResponseEntity<ResponseListData<Co2MeanData>> getDailyMeanCo2Data(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(co2Service.retrieveCo2DailyMeanData(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

    @GetMapping("/week/mean")
    public ResponseEntity<ResponseListData<Co2MeanData>> getWeeklyMeanCo2Data(
            @PathVariable String fieldType, @PathVariable String branchName, @PathVariable String placeName, @PathVariable String sensorNumber, @RequestParam Instant begin, @RequestParam Instant end) {

        return ResponseEntity.status(HttpStatus.OK).body(co2Service.retrieveCo2WeeklyMean(fieldType, branchName, placeName, ENDPOINT, sensorNumber, begin, end));
    }

}
