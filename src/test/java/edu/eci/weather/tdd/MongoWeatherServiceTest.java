package edu.eci.weather.tdd;

import edu.eci.weather.tdd.repository.WeatherReportRepository;
import edu.eci.weather.tdd.repository.document.GeoLocation;
import edu.eci.weather.tdd.repository.document.WeatherReport;
import edu.eci.weather.tdd.controller.weather.dto.WeatherReportDto;
import edu.eci.weather.tdd.exception.WeatherReportNotFoundException;
import edu.eci.weather.tdd.service.MongoWeatherService;
import edu.eci.weather.tdd.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MongoWeatherServiceTest {
    WeatherService weatherService;

    @Mock
    WeatherReportRepository repository;

    @BeforeAll()
    public void setup() {
        weatherService = new MongoWeatherService(repository);
    }

    @Test
    void createWeatherReportCallsSaveOnRepository() {
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation(lat, lng);
        WeatherReportDto weatherReportDto = new WeatherReportDto(location, 35f, 22f, "tester", new Date());
        weatherService.report(weatherReportDto);
        verify(repository).save(any(WeatherReport.class));
    }

    @Test
    void weatherReportIdFoundTest() {
        String weatherReportId = "awae-asd45-1dsad";
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation(lat, lng);
        WeatherReport weatherReport = new WeatherReport(location, 35f, 22f, "tester", new Date());
        when(repository.findById(weatherReportId)).thenReturn(Optional.of(weatherReport));
        WeatherReport foundWeatherReport = weatherService.findById(weatherReportId);
        Assertions.assertEquals(weatherReport, foundWeatherReport);
    }

    @Test
    void weatherReportIdNotFoundTest() {
        String weatherReportId = "dsawe1fasdasdoooq123";
        when(repository.findById(weatherReportId)).thenReturn(Optional.empty());
        Assertions.assertThrows(WeatherReportNotFoundException.class, () -> {
            weatherService.findById(weatherReportId);
        });
    }

    @Test
    void weatherReportReporterFoundTest() {
        String weatherReportReporter = "tester";
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation(lat, lng);
        WeatherReport weatherReport = new WeatherReport(location, 35f, 22f, "tester", new Date());
        WeatherReport weatherReport_two = new WeatherReport(location, 5f, 12f, "tester", new Date());
        WeatherReport weatherReport_three = new WeatherReport(location, 58f, 27f, "tester", new Date());
        List<WeatherReport> weatherReports = new ArrayList<WeatherReport>(){ {add(weatherReport);add(weatherReport_two);add(weatherReport_three);}};
        when(repository.findWeatherReportsByReporter(weatherReportReporter)).thenReturn(weatherReports);
        List<WeatherReport> foundWeatherReports = weatherService.findWeatherReportsByName(weatherReportReporter);
        Assertions.assertEquals(weatherReports, foundWeatherReports);
    }

    @Test
    void weatherReportReporterNotFoundTest() {
        String weatherReportReporter = "anonimo";
        when(repository.findWeatherReportsByReporter(weatherReportReporter)).thenReturn(Collections.emptyList());
        List<WeatherReport> foundWeatherReports = weatherService.findWeatherReportsByName(weatherReportReporter);
        Assertions.assertEquals(Collections.emptyList(), foundWeatherReports);
    }
    //Probar
    @Test
    void weatherReportLocationsBetweenRangesTest(){
        float distanceRangeInMeters = 23;
        double lat = 4.7110;
        double lng = 74.0721;
        GeoLocation location = new GeoLocation(lat, lng);
        double lat1 = 20.7110;
        double lng1 = 10.0721;
        GeoLocation location1 = new GeoLocation(lat1, lng1);
        double lat2 = -8.7110;
        double lng2 = 5.0721;
        GeoLocation location2 = new GeoLocation(lat2, lng2);
        WeatherReport weatherReport = new WeatherReport(location, 35f, 22f, "tester", new Date());
        WeatherReport weatherReport_two = new WeatherReport(location1, 35f, 22f, "tester", new Date());
        WeatherReport weatherReport_three = new WeatherReport(location2, 35f, 22f, "tester", new Date());
        List<WeatherReport> weatherReports = new ArrayList<WeatherReport>(){ {add(weatherReport);add(weatherReport_two);add(weatherReport_three);}};
        when(repository.findNearLocation(lat-distanceRangeInMeters, lat+distanceRangeInMeters,lng-distanceRangeInMeters,lng+distanceRangeInMeters)).thenReturn(weatherReports);
        List<WeatherReport> nearestWeatherReports = weatherService.findNearLocation(location,distanceRangeInMeters);
        Assertions.assertEquals(weatherReports, nearestWeatherReports);
    }
}
