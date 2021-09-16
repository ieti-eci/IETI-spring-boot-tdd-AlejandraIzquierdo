package edu.eci.weather.tdd.repository;

import edu.eci.weather.tdd.repository.document.WeatherReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WeatherReportRepository
    extends MongoRepository<WeatherReport, String>
{
    List<WeatherReport> findWeatherReportsByReporter(String reporter);

    @Query("{ $and: [ {'geoLocation': {'lat': { $gt: ?0, $lt: ?1 } } }, {'geoLocation': {'lng': { $gt: ?2, $lt: ?3 } } } ] }")
    List<WeatherReport> findNearLocation(Double latGT, Double latLT,Double lngGT, Double lngLT);
}
