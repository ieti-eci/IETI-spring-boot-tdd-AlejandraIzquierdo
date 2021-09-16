package edu.eci.weather.tdd;

import edu.eci.weather.tdd.controller.weather.dto.WeatherReportDto;
import edu.eci.weather.tdd.repository.document.GeoLocation;
import edu.eci.weather.tdd.repository.document.WeatherReport;
import edu.eci.weather.tdd.service.WeatherService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherReportControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    WeatherService weatherService;

    @Test
    public void shouldReturnWeatherReportCreated()
            throws Exception {
        WeatherReportDto weatherReportDto = new WeatherReportDto(new GeoLocation(4.777,74.14), 35f, 22f, "tester", new Date());
        WeatherReport weatherReport = new WeatherReport(new GeoLocation(4.777,74.14), 35f, 22f, "tester", new Date());
        when(weatherService.report(weatherReportDto)).thenReturn(weatherReport);
        WeatherReport response = this.restTemplate.postForObject("http://localhost:" + port + "/v1/weather", weatherReportDto, WeatherReport.class );
        verify(weatherService).report(any(WeatherReportDto.class));
    }

    @Test
    public void shouldReturnWeatherReportById()
            throws Exception {
        String weatherReportId = "tester";
        WeatherReport weatherReport = new WeatherReport(new GeoLocation(43.7110, 46.0721), 40, 20, "tester", new Date());
        when(weatherService.findById(weatherReportId)).thenReturn(weatherReport);
        WeatherReport foundWeatherReport = this.restTemplate.getForObject("http://localhost:" + port + "/v1/weather/" + weatherReportId, WeatherReport.class);
        Boolean result = weatherReport.equalContent(foundWeatherReport);
        Assertions.assertEquals(result, true);
    }

}
