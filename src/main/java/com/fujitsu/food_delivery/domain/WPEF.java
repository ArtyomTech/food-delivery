package com.fujitsu.food_delivery.domain;

import com.fujitsu.food_delivery.exception.VehicleTypeForbiddenException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WPEF {

    SNOW_OR_SLEET(1.0),
    RAIN(0.5),
    NO_EXTRA(0.0);

    private final double fee;

    /**
     * Determines the appropriate WPEF (Weather-Phenomenon Effect Fee) based on the given phenomenon and vehicle type.
     * The method applies different fee logic depending on the type of weather phenomenon and whether the vehicle type is not a car.
     *
     * @param phenomenon the weather phenomenon (e.g., SNOW, RAIN)
     * @param vehicleType the type of vehicle (e.g., CAR, SCOOTER, BIKE)
     * @return the corresponding WPEF based on the given conditions
     * @throws VehicleTypeForbiddenException if the selected vehicle type is forbidden for the given phenomenon
     */
    public static WPEF getWPEF(Phenomenon phenomenon, VehicleType vehicleType) {
        if (!isNotCar(vehicleType)) return NO_EXTRA;
        if (isSnowOrSleet(phenomenon)) return SNOW_OR_SLEET;
        else if (isRain(phenomenon)) return RAIN;
        else if (isForbidden(phenomenon)) throw new VehicleTypeForbiddenException(
                "Usage of selected vehicle type is forbidden"
        );
        return NO_EXTRA;
    }

    private static boolean isSnowOrSleet(Phenomenon phenomenon) {
        return phenomenon == Phenomenon.LIGHT_SNOW_SHOWER ||
                phenomenon == Phenomenon.MODERATE_SNOW_SHOWER ||
                phenomenon == Phenomenon.HEAVY_SNOW_SHOWER ||
                phenomenon == Phenomenon.LIGHT_SLEET ||
                phenomenon == Phenomenon.MODERATE_SLEET ||
                phenomenon == Phenomenon.LIGHT_SNOWFALL ||
                phenomenon == Phenomenon.MODERATE_SNOWFALL ||
                phenomenon == Phenomenon.HEAVY_SNOWFALL;
    }

    private static boolean isRain(Phenomenon phenomenon) {
        return phenomenon == Phenomenon.LIGHT_SHOWER ||
                phenomenon == Phenomenon.MODERATE_SHOWER ||
                phenomenon == Phenomenon.HEAVY_SHOWER ||
                phenomenon == Phenomenon.LIGHT_RAIN ||
                phenomenon == Phenomenon.MODERATE_RAIN ||
                phenomenon == Phenomenon.HEAVY_RAIN;
    }

    private static boolean isForbidden(Phenomenon phenomenon) {
        return phenomenon == Phenomenon.GLAZE || phenomenon == Phenomenon.HAIL;
    }

    private static boolean isNotCar(VehicleType vehicleType) {
        return vehicleType == VehicleType.SCOOTER || vehicleType == VehicleType.BIKE;
    }

}
