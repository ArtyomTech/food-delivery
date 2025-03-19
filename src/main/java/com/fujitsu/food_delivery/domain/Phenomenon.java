package com.fujitsu.food_delivery.domain;

import com.fujitsu.food_delivery.exception.UnknownPhenomenonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Phenomenon {

    CLEAR("Clear"),
    FEW_CLOUDS("Few clouds"),
    VARIABLE_CLOUDS("Variable clouds"),
    CLOUDY_WITH_CLEAR_SPELLS("Cloudy with clear spells"),
    OVERCAST("Overcast"),
    LIGHT_SNOW_SHOWER("Light snow shower"),
    MODERATE_SNOW_SHOWER("Moderate snow shower"),
    HEAVY_SNOW_SHOWER("Heavy snow shower"),
    LIGHT_SHOWER("Light shower"),
    MODERATE_SHOWER("Moderate shower"),
    HEAVY_SHOWER("Heavy shower"),
    LIGHT_RAIN("Light rain"),
    MODERATE_RAIN("Moderate rain"),
    HEAVY_RAIN("Heavy rain"),
    GLAZE("Glaze"),
    LIGHT_SLEET("Light sleet"),
    MODERATE_SLEET("Moderate sleet"),
    LIGHT_SNOWFALL("Light snowfall"),
    MODERATE_SNOWFALL("Moderate snowfall"),
    HEAVY_SNOWFALL("Heavy snowfall"),
    HAIL("Hail"),
    MIST("Mist"),
    FOG("Fog");

    private final String value;

    /**
     * Converts a string value to its corresponding Phenomenon enum.
     * The method performs a case-insensitive comparison to find the matching phenomenon.
     *
     * @param value the string value representing a weather phenomenon
     * @return the corresponding Phenomenon enum based on the input value
     * @throws UnknownPhenomenonException if no matching Phenomenon is found
     */
    public static Phenomenon fromValue(String value) {
        for (Phenomenon phenomenon : Phenomenon.values()) {
            if (phenomenon.getValue().equalsIgnoreCase(value)) return phenomenon;
        }
        throw new UnknownPhenomenonException("Unknown phenomenon: " + value);
    }

}
