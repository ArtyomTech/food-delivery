package com.fujitsu.food_delivery.service;

import com.fujitsu.food_delivery.config.WeatherProperties;
import com.fujitsu.food_delivery.dto.WeatherDto;
import com.fujitsu.food_delivery.entity.WeatherEntity;
import com.fujitsu.food_delivery.mapper.WeatherMapper;
import com.fujitsu.food_delivery.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherMapper weatherMapper;
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final WeatherProperties weatherProperties;

    /**
     * A scheduled task that fetches weather data at a specified interval, maps the data to weather entities,
     * filters the entities by the specified stations, and saves them to the repository.
     * <p>
     * The schedule is defined by a cron expression provided in the {@link WeatherProperties}.
     * This method is invoked automatically by the Spring scheduling mechanism based on the cron schedule.
     * </p>
     */
    @Scheduled(cron = "#{weatherProperties.cron}")
    @Transactional
    public void scheduleFetchWeather() {
        WeatherDto weatherDto = fetchWeather();
        List<WeatherEntity> weatherEntities = weatherMapper.mapToEntities(weatherDto);
        List<WeatherEntity> weathersByStations = weatherEntities
                .stream()
                .filter(
                        weatherEntity ->
                                weatherProperties.getStations().contains(weatherEntity.getStationName())
                )
                .toList();
        weatherRepository.saveAll(weathersByStations);
    }

    private WeatherDto fetchWeather() {
        return restTemplate.getForObject(weatherProperties.getUrl(), WeatherDto.class);
    }

}
