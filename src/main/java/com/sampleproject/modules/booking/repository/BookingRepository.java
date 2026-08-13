package com.sampleproject.modules.booking.repository;

import com.sampleproject.modules.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
