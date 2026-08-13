package com.sampleproject.modules.booking.service;

import com.sampleproject.modules.booking.dto.BookingRequest;
import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingResponse createBooking(BookingRequest request){

    }

    public void payWithCoupon(){}

    public void approveBooking(){}

    public void rejectBooking(){}

    public void assignPilot(){}

    public void myBookings(){}

    public void allBookings(){}

    public void bookingDetails(){}
}
