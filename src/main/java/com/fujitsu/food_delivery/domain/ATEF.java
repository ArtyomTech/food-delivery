package com.fujitsu.food_delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ATEF {

    NOT_CAR_TEMP_LESS_THAN_10(1.0),
    NOT_CAR_TEMP_BETWEEN_MINUS10_AND_0(0.5),
    NO_EXTRA(0.0);

    private final double fee;

    /**
     * Determines the appropriate ATEF (Additional Temperature Effect Fee)
     * based on the given temperature and vehicle type.
     *
     * @param temperature the air temperature in degrees Celsius
     * @param vehicleType the type of vehicle (e.g., CAR, SCOOTER, BIKE)
     * @return the corresponding ATEF value based on the given conditions
     */
    public static ATEF getATEF(double temperature, VehicleType vehicleType) {
        if (vehicleType == VehicleType.CAR) return NO_EXTRA;
        if (temperature < -10 && isNotCar(vehicleType)) return NOT_CAR_TEMP_LESS_THAN_10;
        else if (temperature >= -10 && temperature <= 0 && isNotCar(vehicleType)) return NOT_CAR_TEMP_BETWEEN_MINUS10_AND_0;
        else return NO_EXTRA;
    }

    private static boolean isNotCar(VehicleType vehicleType) {
        return VehicleType.SCOOTER == vehicleType || VehicleType.BIKE == vehicleType;
    }

}
