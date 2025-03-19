package com.fujitsu.food_delivery.service;

import com.fujitsu.food_delivery.domain.*;
import com.fujitsu.food_delivery.exception.NoWeatherDataException;
import com.fujitsu.food_delivery.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

    private final WeatherRepository weatherRepository;

    /**
     * Calculates the delivery fee based on the provided city, vehicle type, and optional timestamp.
     * This method retrieves the latest weather data for the specified city and, if a timestamp is provided,
     * calculates the fee by applying different factors including RBF, ATEF, WSEF, and WPEF.
     *
     * @param city the city for which the delivery fee is being calculated
     * @param vehicleType the type of vehicle used for the delivery
     * @param datetime the optional datetime string (in the format "yyyy-MM-dd HH:mm:ss") representing the timestamp
     *                 for which the weather data is to be considered. If null, the latest available weather data is used.
     * @return the calculated delivery fee based on the weather conditions and vehicle type
     * @throws NoWeatherDataException if no weather data is available for the specified city and timestamp
     */
    public Double calculateDeliveryFee(City city, VehicleType vehicleType, String datetime) {
        LocalDateTime timestamp = null;
        if (datetime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            timestamp = LocalDateTime.parse(datetime, formatter);
        }
        return weatherRepository.findLatestByStationNameAndOptionalTimestamp(city.getValue(), timestamp)
                .map(weatherEntity -> {
                    RBF rbf = RBF.getRBF(city, vehicleType);
                    ATEF atef = ATEF.getATEF(weatherEntity.getAirTemperature(), vehicleType);
                    WSEF wsef = WSEF.getWSEF(weatherEntity.getWindSpeed(), vehicleType);
                    WPEF wpef = WPEF.getWPEF(weatherEntity.getPhenomenon(), vehicleType);
                    return rbf.getFee() + atef.getFee() + wsef.getFee() + wpef.getFee();
                })
                .orElseThrow(() -> new NoWeatherDataException("No weather data available for city: " + city.name()));
    }

}
