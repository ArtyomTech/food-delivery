package com.fujitsu.food_delivery.controller;

import com.fujitsu.food_delivery.domain.City;
import com.fujitsu.food_delivery.domain.VehicleType;
import com.fujitsu.food_delivery.service.DeliveryFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    /**
     * Handles GET requests to retrieve the delivery fee based on the given city, vehicle type,
     * and an optional timestamp for historical weather-based calculations.
     *
     * <p><b>Note:</b> The input values for {@code city} and {@code vehicleType} must be provided
     * in uppercase, as defined in their respective enums.</p>
     *
     * <ul>
     *     <li><b>Valid values for city:</b> TALLINN, TARTU, PARNU</li>
     *     <li><b>Valid values for vehicleType:</b> CAR, SCOOTER, BIKE</li>
     * </ul>
     *
     * @param city        the city for which the delivery fee is being requested (must be in uppercase)
     * @param vehicleType the type of vehicle used for delivery (must be in uppercase)
     * @param datetime    an optional timestamp (formatted as "yyyy-MM-dd HH:mm:ss")
     *                    to calculate the fee based on historical weather conditions
     * @return a ResponseEntity containing the calculated delivery fee
     */
    @GetMapping("delivery-fee")
    public ResponseEntity<Double> getDeliveryFee(
            @RequestParam City city,
            @RequestParam VehicleType vehicleType,
            @RequestParam(required = false) String datetime
    ) {
        Double deliveryFee = deliveryFeeService.calculateDeliveryFee(city, vehicleType, datetime);
        return ResponseEntity.ok(deliveryFee);
    }

}
