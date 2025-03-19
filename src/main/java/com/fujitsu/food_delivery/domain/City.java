package com.fujitsu.food_delivery.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum City {

    TALLINN("Tallinn-Harku"),
    TARTU("Tartu-Tõravere"),
    PARNU("Pärnu");

    private final String value;

}
