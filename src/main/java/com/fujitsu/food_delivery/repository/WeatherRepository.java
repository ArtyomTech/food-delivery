package com.fujitsu.food_delivery.repository;

import com.fujitsu.food_delivery.entity.WeatherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Long> {

    @Query(
           """
           SELECT w
           FROM WeatherEntity w
           WHERE w.stationName = :stationName
           AND (:timestamp IS NULL OR w.timestamp <= :timestamp)
           ORDER BY w.timestamp DESC
           """
    )
    Optional<WeatherEntity> findLatestByStationNameAndOptionalTimestamp(
            @Param("stationName") String stationName,
            @Param("timestamp") LocalDateTime timestamp
    );

}

