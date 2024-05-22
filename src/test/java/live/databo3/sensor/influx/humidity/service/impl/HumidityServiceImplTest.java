package live.databo3.sensor.influx.humidity.service.impl;

import live.databo3.sensor.influx.dto.ResponseListData;
import live.databo3.sensor.influx.dto.ResponseSingleData;
import live.databo3.sensor.influx.humidity.measurement.*;
import live.databo3.sensor.influx.influxdb_repository.InfluxDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HumidityServiceImplTest {

    @Mock
    private InfluxDBRepository influxDBRepository;

    @InjectMocks
    private HumidityServiceImpl humidityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveHumidityLastData() {
        HumidityLastData lastData = new HumidityLastData();
        when(influxDBRepository.findLastData(anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityLastData.class)))
                .thenReturn(Optional.of(lastData));

        ResponseSingleData<HumidityLastData> response = humidityService.retrieveHumidityLastData("field", "branch", "place", "endpoint", "sensor");

        assertNotNull(response);
        assertEquals(lastData, response.getData());
        verify(influxDBRepository, times(1)).findLastData(anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityLastData.class));
    }

    @Test
    void testRetrieveHumidityHourlyMeanData() {
        List<HumidityMeanData> dataList = Collections.singletonList(new HumidityMeanData());
        when(influxDBRepository.findFiveMinutesMeanDataForOneHour(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMeanData.class)))
                .thenReturn(dataList);

        ResponseListData<HumidityMeanData> response = humidityService.retrieveHumidityHourlyMeanData("field", "branch", "place", "endpoint", "sensor", Instant.now(), Instant.now());

        assertNotNull(response);
        assertEquals(dataList, response.getData());
        verify(influxDBRepository, times(1)).findFiveMinutesMeanDataForOneHour(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMeanData.class));
    }

    @Test
    void testRetrieveHumidityDailyMeanData() {
        List<HumidityMeanData> dataList = Collections.singletonList(new HumidityMeanData());
        when(influxDBRepository.findOneHourMeanDataForOneDay(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMeanData.class)))
                .thenReturn(dataList);

        ResponseListData<HumidityMeanData> response = humidityService.retrieveHumidityDailyMeanData("field", "branch", "place", "endpoint", "sensor", Instant.now(), Instant.now());

        assertNotNull(response);
        assertEquals(dataList, response.getData());
        verify(influxDBRepository, times(1)).findOneHourMeanDataForOneDay(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMeanData.class));
    }

    @Test
    void testRetrieveHumidityWeeklyMinData() {
        List<HumidityMinData> dataList = Collections.singletonList(new HumidityMinData());
        when(influxDBRepository.findOneDayMinMaxDataForOneWeek(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMinData.class)))
                .thenReturn(dataList);

        ResponseListData<HumidityMinData> response = humidityService.retrieveHumidityWeeklyMinData("field", "branch", "place", "endpoint", "sensor", Instant.now(), Instant.now());

        assertNotNull(response);
        assertEquals(dataList, response.getData());
        verify(influxDBRepository, times(1)).findOneDayMinMaxDataForOneWeek(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMinData.class));
    }

    @Test
    void testRetrieveHumidityWeeklyMaxData() {
        List<HumidityMaxData> dataList = Collections.singletonList(new HumidityMaxData());
        when(influxDBRepository.findOneDayMinMaxDataForOneWeek(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMaxData.class)))
                .thenReturn(dataList);

        ResponseListData<HumidityMaxData> response = humidityService.retrieveHumidityWeeklyMaxData("field", "branch", "place", "endpoint", "sensor", Instant.now(), Instant.now());

        assertNotNull(response);
        assertEquals(dataList, response.getData());
        verify(influxDBRepository, times(1)).findOneDayMinMaxDataForOneWeek(any(Instant.class), any(Instant.class), anyString(), anyString(), anyString(), anyString(), anyString(), eq(HumidityMaxData.class));
    }
}
