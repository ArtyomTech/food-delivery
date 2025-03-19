package com.fujitsu.food_delivery.domain;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum StationName {

    TALLINN_HARKU("Tallinn-Harku"),
    TARTU_TORAVERE("Tartu-Tõravere"),
    PARNU("Pärnu");

    private final String value;

    /**
     * Retrieves a set of allowed station names.
     * This method returns all station names defined in the enum as a set of strings.
     *
     * @return a set containing the string values of all station names
     */
    public static Set<String> getAllowedStationNames() {
        return Arrays.stream(values())
                .map(stationName -> stationName.value)
                .collect(Collectors.toSet());
    }

}
