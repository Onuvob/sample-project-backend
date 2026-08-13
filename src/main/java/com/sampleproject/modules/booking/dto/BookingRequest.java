package com.sampleproject.modules.booking.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long vehicleId;
    private Long routeId;
    private String couponCode;
}
