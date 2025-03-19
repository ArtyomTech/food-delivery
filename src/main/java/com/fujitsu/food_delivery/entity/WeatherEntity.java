package com.fujitsu.food_delivery.entity;

import com.fujitsu.food_delivery.domain.Phenomenon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Table(name = "weather")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stationName;

    private String wmoCode;

    private Double airTemperature;

    private Double windSpeed;

    @Enumerated(EnumType.STRING)
    private Phenomenon phenomenon;

    private LocalDateTime timestamp;

    /**
     * Sets the timestamp for weather entity.
     *
     * @param timestamp the epoch second timestamp to be converted
     *                  to LocalDateTime in the "Europe/Tallinn" time zone
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.of("Europe/Tallinn"))
                .toLocalDateTime();
    }

}
