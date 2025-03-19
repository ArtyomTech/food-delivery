package com.fujitsu.food_delivery.service;

import com.fujitsu.food_delivery.config.WeatherProperties;
import com.fujitsu.food_delivery.dto.StationDto;
import com.fujitsu.food_delivery.dto.WeatherDto;
import com.fujitsu.food_delivery.mapper.WeatherMapper;
import com.fujitsu.food_delivery.mapper.WeatherMapperImpl;
import com.fujitsu.food_delivery.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private static final String URL = "http://weather-api.com";
    private static final Long TIMESTAMP = 1742347380L;

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherProperties weatherProperties;

    @Spy
    WeatherMapper weatherMapper = new WeatherMapperImpl();

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void scheduleFetchWeather_shouldFetchAndSaveWeatherData() {
        WeatherDto weatherDto = WeatherDto.builder()
                .timestamp(TIMESTAMP)
                .stations(List.of(createStationDto("Tallinn-Harku")))
                .build();

        when(weatherProperties.getUrl()).thenReturn(URL);
        when(restTemplate.getForObject(anyString(), eq(WeatherDto.class))).thenReturn(weatherDto);

        weatherService.scheduleFetchWeather();

        verify(weatherRepository).saveAll(anyList());
        verify(weatherMapper).mapToEntities(weatherDto);
    }

    @Test
    void scheduleFetchWeather_noWeatherDataToSave() {
        WeatherDto weatherDto = WeatherDto.builder()
                .timestamp(TIMESTAMP)
                .stations(List.of(createStationDto("INVALID_CITY")))
                .build();

        when(weatherProperties.getUrl()).thenReturn(URL);
        when(restTemplate.getForObject(anyString(), eq(WeatherDto.class))).thenReturn(weatherDto);

        weatherService.scheduleFetchWeather();

        verify(weatherRepository).saveAll(Collections.emptyList());
        verify(weatherMapper).mapToEntities(weatherDto);
    }

    private static StationDto createStationDto(String name) {
        return StationDto.builder()
                .name(name)
                .phenomenon("Clear")
                .airtemperature(22.5)
                .windspeed(5.0)
                .build();
    }

}
