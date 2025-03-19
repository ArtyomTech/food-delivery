package com.fujitsu.food_delivery.dto;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@XmlRootElement(name = "observations")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {

    @XmlAttribute(name = "timestamp")
    private Long timestamp;

    @XmlElement(name = "station")
    private List<StationDto> stations;

}
