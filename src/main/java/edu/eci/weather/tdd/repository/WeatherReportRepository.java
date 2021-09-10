package edu.eci.weather.tdd.repository;

import edu.eci.weather.tdd.repository.document.WeatherReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeatherReportRepository
    extends MongoRepository<WeatherReport, String>
{
}
