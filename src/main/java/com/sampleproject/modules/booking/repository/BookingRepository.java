package com.sampleproject.modules.booking.repository;

import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.entity.Booking;
import com.sampleproject.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT new com.sampleproject.modules.booking.dto.BookingResponse(b.id, owner.firstName," +
            "owner.lastName, vehicle.registrationNumber, vehicle.name, vehicle.type, " +
            "vehicle.capacity, route.source, route.destination, coupon.code, coupon.amount, " +
            "pilot.name, pilot.phone, b.amount, b.paymentStatus, b.bookingStatus, b.createdAt, b.updatedAt) " +
            "FROM Booking b LEFT JOIN b.owner owner " +
            "LEFT JOIN b.vehicle vehicle " +
            "LEFT JOIN b.route route " +
            "LEFT JOIN b.coupon coupon " +
            "LEFT JOIN b.pilot pilot WHERE b.owner = :owner")
    Page<BookingResponse> getSelfPaginatedList(@Param("owner") User owner, Pageable pageable);

    @Query("SELECT new com.sampleproject.modules.booking.dto.BookingResponse(b.id, owner.firstName," +
            "owner.lastName, vehicle.registrationNumber, vehicle.name, vehicle.type, " +
            "vehicle.capacity, route.source, route.destination, coupon.code, coupon.amount, " +
            "pilot.name, pilot.phone, b.amount, b.paymentStatus, b.bookingStatus, b.createdAt, b.updatedAt) " +
            "FROM Booking b LEFT JOIN b.owner owner " +
            "LEFT JOIN b.vehicle vehicle " +
            "LEFT JOIN b.route route " +
            "LEFT JOIN b.coupon coupon " +
            "LEFT JOIN b.pilot pilot")
    Page<BookingResponse> getPaginatedList(Pageable pageable);
}
