package com.sampleproject.modules.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotNull(message = "Vehicle is required")
    private Long vehicleId;
    @NotNull(message = "Route is required")
    private Long routeId;
    @NotBlank(message = "Coupon code is required")
    private String couponCode;
}
