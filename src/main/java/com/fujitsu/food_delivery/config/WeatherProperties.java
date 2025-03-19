package com.fujitsu.food_delivery.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "weather")
public class WeatherProperties {

    private String url;
    private String cron;
    private List<String> stations;

}
