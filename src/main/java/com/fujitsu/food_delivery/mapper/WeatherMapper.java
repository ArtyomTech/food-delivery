package com.fujitsu.food_delivery.mapper;

import com.fujitsu.food_delivery.domain.Phenomenon;
import com.fujitsu.food_delivery.domain.StationName;
import com.fujitsu.food_delivery.dto.StationDto;
import com.fujitsu.food_delivery.dto.WeatherDto;
import com.fujitsu.food_delivery.entity.WeatherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", ignore = true)
    @Mapping(target = "stationName", source = "name")
    @Mapping(target = "wmoCode", source = "wmocode")
    @Mapping(target = "airTemperature", source = "airtemperature")
    @Mapping(target = "windSpeed", source = "windspeed")
    @Mapping(target = "phenomenon", source = "phenomenon")
    WeatherEntity mapToEntity(StationDto dto);

    List<WeatherEntity> mapToEntities(List<StationDto> dtos);

    default List<WeatherEntity> mapToEntities(WeatherDto weatherDto) {
        List<WeatherEntity> weatherEntities = mapToEntities(
                weatherDto
                        .getStations()
                        .stream()
                        .filter(station -> StationName.getAllowedStationNames().contains(station.getName()))
                        .toList()
        );
        weatherEntities.forEach(entity -> entity.setTimestamp(weatherDto.getTimestamp()));
        return weatherEntities;
    }

    default Phenomenon mapPhenomenon(String phenomenon) {
        return Phenomenon.fromValue(phenomenon);
    }

}
