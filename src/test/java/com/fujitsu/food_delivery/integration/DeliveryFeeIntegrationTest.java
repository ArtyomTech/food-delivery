package com.fujitsu.food_delivery.integration;

import com.fujitsu.food_delivery.domain.Phenomenon;
import com.fujitsu.food_delivery.entity.WeatherEntity;
import com.fujitsu.food_delivery.repository.WeatherRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryFeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeEach
    void setUp() {
        WeatherEntity weatherEntity = WeatherEntity.builder()
                .stationName("Tallinn-Harku")
                .airTemperature(5.0)
                .windSpeed(0.0)
                .phenomenon(Phenomenon.CLEAR)
                .build();
        weatherRepository.save(weatherEntity);
    }

    @AfterEach
    void tearDown() {
        weatherRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void getDeliveryFee_shouldReturnOk_whenValidRequest() {
        mockMvc.perform(
                        get("/delivery-fee")
                                .param("city", "TALLINN")
                                .param("vehicleType", "CAR")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(4.0)));
    }

    @Test
    @SneakyThrows
    void getDeliveryFee_shouldReturnBadRequest_whenInvalidCity() {
        mockMvc.perform(
                get("/delivery-fee")
                        .param("city", "INVALID_CITY")
                        .param("vehicleType", "CAR")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void getDeliveryFee_shouldReturnBadRequest_whenInvalidVehicleType() {
        mockMvc.perform(
                get("/delivery-fee")
                        .param("city", "TALLINN")
                        .param("vehicleType", "INVALID_VEHICLE")
                )
                .andExpect(status().isBadRequest());
    }

}
