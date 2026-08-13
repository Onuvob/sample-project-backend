package com.sampleproject.modules.booking.mapper;

import com.sampleproject.modules.booking.dto.BookingRequest;
import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.entity.Booking;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.modules.route.entity.Route;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());

        if (booking.getOwner() != null) {
            response.setOwnerFirstName(booking.getOwner().getFirstName());
            response.setOwnerLastName(booking.getOwner().getLastName());
        }

        if (booking.getVehicle() != null) {
            response.setVehicleRegNo(booking.getVehicle().getRegistrationNumber());
            response.setVehicleName(booking.getVehicle().getName());
            response.setVehicleType(booking.getVehicle().getType());
            response.setVehicleCapacity(booking.getVehicle().getCapacity());
        }

        if (booking.getRoute() != null) {
            response.setRouteSource(booking.getRoute().getSource());
            response.setRouteDestination(booking.getRoute().getDestination());
        }

        if (booking.getCoupon() != null) {
            response.setCouponCode(booking.getCoupon().getCode());
            response.setCouponAmount(booking.getCoupon().getAmount());
        }

        if (booking.getPilot() != null) {
            response.setPilotName(booking.getPilot().getName());
            response.setPilotPhone(booking.getPilot().getPhone());
        }

        response.setAmount(booking.getAmount());
        response.setPaymentStatus(booking.getPaymentStatus());
        response.setBookingStatus(booking.getBookingStatus());
        response.setCreatedAt(booking.getCreatedAt());
        response.setUpdatedAt(booking.getUpdatedAt());

        return response;
    }

    public List<BookingResponse> toResponseList(List<Booking> bookings) {
        if (bookings == null) {
            return Collections.emptyList();
        }

        return bookings.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Booking toEntity(
            BookingRequest request,
            User owner,
            Vehicle vehicle,
            Route route,
            Coupon coupon
    ) {
        if (request == null) {
            return null;
        }

        return Booking.builder()
                .owner(owner)
                .vehicle(vehicle)
                .route(route)
                .coupon(coupon)
                .amount(request.getAmount())
                .build();
    }
}
