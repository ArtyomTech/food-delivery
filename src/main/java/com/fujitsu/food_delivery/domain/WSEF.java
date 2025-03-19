package com.fujitsu.food_delivery.domain;

import com.fujitsu.food_delivery.exception.VehicleTypeForbiddenException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WSEF {

    BIKE_WIND_BETWEEN_10_AND_20(0.5),
    NO_EXTRA(0.0);

    private final double fee;

    /**
     * Determines the appropriate WSEF (Wind Speed Effect Fee) based on the given wind speed and vehicle type.
     * This method applies a fee if the vehicle type is a bike and the wind speed falls between 10 and 20.
     * It also throws an exception if the wind speed exceeds 20 for a bike.
     *
     * @param windSpeed the wind speed in meters per second
     * @param vehicleType the type of vehicle (e.g., BIKE)
     * @return the corresponding WSEF based on the given conditions
     * @throws IllegalArgumentException if the wind speed exceeds 20 for a bike
     */
    public static WSEF getWSEF(double windSpeed, VehicleType vehicleType) {
        if (vehicleType != VehicleType.BIKE) return NO_EXTRA;
        if (windSpeed > 20) throw new VehicleTypeForbiddenException("Usage of selected vehicle type is forbidden");
        else if (windSpeed >= 10) return BIKE_WIND_BETWEEN_10_AND_20;
        else return NO_EXTRA;
    }

}
