package com.fujitsu.food_delivery.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "wmocode")
    private String wmocode;

    @XmlElement(name = "longitude")
    private Double longitude;

    @XmlElement(name = "latitude")
    private Double latitude;

    @XmlElement(name = "phenomenon")
    private String phenomenon;

    @XmlElement(name = "visibility")
    private Double visibility;

    @XmlElement(name = "precipitations")
    private Double precipitations;

    @XmlElement(name = "airpressure")
    private Double airpressure;

    @XmlElement(name = "relativehumidity")
    private Double relativehumidity;

    @XmlElement(name = "airtemperature")
    private Double airtemperature;

    @XmlElement(name = "winddirection")
    private String winddirection;

    @XmlElement(name = "windspeed")
    private Double windspeed;

    @XmlElement(name = "windspeedmax")
    private Double windspeedmax;

    @XmlElement(name = "waterlevel")
    private Double waterlevel;

    @XmlElement(name = "waterlevel_eh2000")
    private Double waterlevel_eh2000;

    @XmlElement(name = "watertemperature")
    private Double watertemperature;

    @XmlElement(name = "uvindex")
    private Double uvindex;

    @XmlElement(name = "sunshineduration")
    private Double sunshineduration;

    @XmlElement(name = "globalradiation")
    private Double globalradiation;

}
