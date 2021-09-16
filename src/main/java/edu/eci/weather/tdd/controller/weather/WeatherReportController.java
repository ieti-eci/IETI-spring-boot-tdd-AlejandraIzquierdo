package edu.eci.weather.tdd.controller.weather;

import edu.eci.weather.tdd.repository.document.WeatherReport;
import edu.eci.weather.tdd.controller.weather.dto.NearByWeatherReportsQueryDto;
import edu.eci.weather.tdd.controller.weather.dto.WeatherReportDto;
import edu.eci.weather.tdd.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/v1/weather" )
public class WeatherReportController
{
    private final WeatherService weatherService;

    public WeatherReportController( @Autowired WeatherService weatherService )
    {
        this.weatherService = weatherService;
    }

    @PostMapping
    public WeatherReport create(@RequestBody WeatherReportDto weatherReportDto )
    {
        return weatherService.report( weatherReportDto );
    }

    @GetMapping( "/{id}" )
    public WeatherReport findById( @PathVariable String id )
    {
        return weatherService.findById( id );
    }

    @PostMapping( "/nearby" )
    public List<WeatherReport> findNearByReports( @RequestBody NearByWeatherReportsQueryDto query )
    {
        return weatherService.findNearLocation( query.getGeoLocation(), query.getDistanceRangeInMeters() );
    }

    @GetMapping( "reporter/{id}" )
    public List<WeatherReport> findByReporterId( @PathVariable String name )
    {
        return weatherService.findWeatherReportsByName( name );
    }
}
