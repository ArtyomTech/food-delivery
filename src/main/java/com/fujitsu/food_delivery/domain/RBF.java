package com.fujitsu.food_delivery.domain;

import com.fujitsu.food_delivery.exception.NoRBFFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RBF {

    TALLINN_CAR(4.0),
    TALLINN_SCOOTER(3.5),
    TALLINN_BIKE(3.0),

    TARTU_CAR(3.5),
    TARTU_SCOOTER(3.0),
    TARTU_BIKE(2.5),

    PARNU_CAR(3.0),
    PARNU_SCOOTER(2.5),
    PARNU_BIKE(2.0);

    private final double fee;

    /**
     * Retrieves the appropriate RBF (Region Base Fee) based on the given city and vehicle type.
     * The method constructs a key using the city and vehicle type names and matches it with the enum constant.
     *
     * @param city the city for which the base fee is being requested (e.g., TALLINN, TARTU, PARNU)
     * @param vehicleType the type of vehicle (e.g., CAR, SCOOTER, BIKE)
     * @return the corresponding RBF enum constant based on the city and vehicle type
     * @throws NoRBFFoundException if no matching RBF is found for the given city and vehicle type
     */
    public static RBF getRBF(City city, VehicleType vehicleType) {
        String key = city.name() + "_" + vehicleType.name();
        for (RBF RBF : values()) if (RBF.name().equals(key)) return RBF;
        throw new NoRBFFoundException("Base fee not found for " + city + " and " + vehicleType);
    }

}
