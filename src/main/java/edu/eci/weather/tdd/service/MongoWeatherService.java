package edu.eci.weather.tdd.service;

import edu.eci.weather.tdd.repository.WeatherReportRepository;
import edu.eci.weather.tdd.repository.document.GeoLocation;
import edu.eci.weather.tdd.repository.document.WeatherReport;
import edu.eci.weather.tdd.controller.weather.dto.WeatherReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoWeatherService
    implements WeatherService
{

    private final WeatherReportRepository repository;

    public MongoWeatherService( @Autowired WeatherReportRepository repository )
    {
        this.repository = repository;
    }

    @Override
    public WeatherReport report(WeatherReportDto weatherReportDto )
    {
        return null;
    }

    @Override
    public WeatherReport findById( String id )
    {
        throw new RuntimeException( "Implement this method" );
    }

    @Override
    public List<WeatherReport> findNearLocation(GeoLocation geoLocation, float distanceRangeInMeters )
    {
        return null;
    }

    @Override
    public List<WeatherReport> findWeatherReportsByName( String reporter )
    {
        return null;
    }
}
