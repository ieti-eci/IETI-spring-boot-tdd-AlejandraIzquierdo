package edu.eci.weather.tdd.service;

import edu.eci.weather.tdd.repository.document.GeoLocation;
import edu.eci.weather.tdd.repository.document.WeatherReport;
import edu.eci.weather.tdd.controller.weather.dto.WeatherReportDto;

import java.util.List;

public interface WeatherService
{
    WeatherReport report(WeatherReportDto weatherReportDto );

    WeatherReport findById( String id );

    List<WeatherReport> findNearLocation(GeoLocation geoLocation, float distanceRangeInMeters );

    List<WeatherReport> findWeatherReportsByName( String reporter );

}