package com.sampleproject.modules.booking.dto;

import com.sampleproject.common.enums.BookingStatus;
import com.sampleproject.common.enums.PaymentStatus;
import com.sampleproject.modules.coupon.dto.CouponResponse;
import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.route.dto.RouteResponse;
import com.sampleproject.modules.vessel.dto.VehicleResponse;
import com.sampleproject.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private String ownerFirstName;
    private String ownerLastName;
    private String vehicleRegNo;
    private String vehicleName;
    private String vehicleType;
    private Double vehicleCapacity;
    private String routeSource;
    private String routeDestination;
    private String couponCode;
    private Double couponAmount;
    private String pilotName;
    private String pilotPhone;
    private Double amount;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
