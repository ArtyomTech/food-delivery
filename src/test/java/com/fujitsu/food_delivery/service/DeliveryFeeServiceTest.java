package com.fujitsu.food_delivery.service;

import com.fujitsu.food_delivery.domain.City;
import com.fujitsu.food_delivery.domain.Phenomenon;
import com.fujitsu.food_delivery.domain.VehicleType;
import com.fujitsu.food_delivery.entity.WeatherEntity;
import com.fujitsu.food_delivery.exception.NoWeatherDataException;
import com.fujitsu.food_delivery.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryFeeServiceTest {

    public static final String DATETIME = "2024-03-19 12:00:00";

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private DeliveryFeeService deliveryFeeService;

    @ParameterizedTest
    @MethodSource("provideCityAndVehicleTypeAndTemperature")
    void calculateDeliveryFee_basedOnCityAndVehicleTypeAndTemperature_receivesDeliveryFee(
            City city,
            VehicleType vehicleType,
            double temperature,
            double expectedFee
    ) {
        LocalDateTime timestamp = LocalDateTime.parse(DATETIME, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        WeatherEntity weatherEntity = WeatherEntity.builder()
                .stationName(city.getValue())
                .airTemperature(temperature)
                .windSpeed(0.0)
                .phenomenon(Phenomenon.OVERCAST)
                .timestamp(timestamp)
                .build();

        when(weatherRepository.findLatestByStationNameAndOptionalTimestamp(anyString(), any()))
                .thenReturn(Optional.of(weatherEntity));

        Double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, DATETIME);

        assertEquals(expectedFee, fee);
    }

    @ParameterizedTest
    @MethodSource("provideCityAndVehicleTypeAndPhenomenon")
    void calculateDeliveryFee_basedOnCityAndVehicleTypeAndPhenomenon_receivesDeliveryFee(
            City city,
            VehicleType vehicleType,
            Phenomenon phenomenon,
            double expectedFee
    ) {
        LocalDateTime timestamp = LocalDateTime.parse(DATETIME, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        WeatherEntity weatherEntity = WeatherEntity.builder()
                .stationName(city.getValue())
                .airTemperature(0.0)
                .windSpeed(0.0)
                .phenomenon(phenomenon)
                .timestamp(timestamp)
                .build();

        when(weatherRepository.findLatestByStationNameAndOptionalTimestamp(anyString(), any()))
                .thenReturn(Optional.of(weatherEntity));

        Double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, DATETIME);

        assertEquals(expectedFee, fee);
    }

    @ParameterizedTest
    @MethodSource("provideCityAndVehicleTypeAndWindSpeed")
    void calculateDeliveryFee_basedOnCityAndVehicleTypeAndWindSpeed_receivesDeliveryFee(
            City city,
            VehicleType vehicleType,
            double windSpeed,
            double expectedFee
    ) {
        LocalDateTime timestamp = LocalDateTime.parse(DATETIME, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        WeatherEntity weatherEntity = WeatherEntity.builder()
                .stationName(city.getValue())
                .airTemperature(0.0)
                .windSpeed(windSpeed)
                .phenomenon(Phenomenon.CLEAR)
                .timestamp(timestamp)
                .build();

        when(weatherRepository.findLatestByStationNameAndOptionalTimestamp(anyString(), any()))
                .thenReturn(Optional.of(weatherEntity));

        Double fee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, DATETIME);

        assertEquals(expectedFee, fee);
    }

    @Test
    void calculateDeliveryFee_throwsNoWeatherDataException() {
        City city = City.TALLINN;
        VehicleType vehicleType = VehicleType.CAR;
        when(weatherRepository.findLatestByStationNameAndOptionalTimestamp(anyString(), any()))
                .thenReturn(Optional.empty());

        assertThrows(
                NoWeatherDataException.class,
                () -> deliveryFeeService.calculateDeliveryFee(city, vehicleType, null)
        );
    }

    private static Stream<Arguments> provideCityAndVehicleTypeAndTemperature() {
        return Stream.of(
                Arguments.of(City.TALLINN, VehicleType.CAR, 0.0, 4.0),
                Arguments.of(City.TARTU, VehicleType.SCOOTER, -5.0, 3.5),
                Arguments.of(City.PARNU, VehicleType.BIKE, -15.0, 3.0)
        );
    }

    private static Stream<Arguments> provideCityAndVehicleTypeAndPhenomenon() {
        return Stream.of(
                Arguments.of(City.TALLINN, VehicleType.CAR, Phenomenon.HEAVY_RAIN, 4.0),
                Arguments.of(City.TARTU, VehicleType.SCOOTER, Phenomenon.LIGHT_RAIN, 4.0),
                Arguments.of(City.PARNU, VehicleType.BIKE, Phenomenon.CLEAR, 2.5)
        );
    }

    private static Stream<Arguments> provideCityAndVehicleTypeAndWindSpeed() {
        return Stream.of(
                Arguments.of(City.TALLINN, VehicleType.CAR, 0.0, 4.0),
                Arguments.of(City.TARTU, VehicleType.SCOOTER, 0.0, 3.5),
                Arguments.of(City.PARNU, VehicleType.BIKE, 10.0, 3.0)
        );
    }

}