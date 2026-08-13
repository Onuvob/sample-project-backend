package com.sampleproject.modules.booking.service;

import com.sampleproject.modules.booking.dto.BookingRequest;
import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.entity.Booking;
import com.sampleproject.modules.booking.mapper.BookingMapper;
import com.sampleproject.modules.booking.repository.BookingRepository;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.modules.coupon.service.CouponService;
import com.sampleproject.modules.pilot.service.PilotService;
import com.sampleproject.modules.route.entity.Route;
import com.sampleproject.modules.route.service.RouteService;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.modules.vessel.service.VehicleService;
import com.sampleproject.user.entity.User;
import com.sampleproject.util.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleService vehicleService;
    private final RouteService routeService;
    private final CouponService couponService;
    private final PilotService pilotService;
    private final BookingMapper bookingMapper;
    private final CurrentUserService currentUserService;

    public BookingResponse createBooking(BookingRequest request){

        User owner = this.currentUserService.getCurrentUser();
        Vehicle vehicle = this.vehicleService.getById(request.getVehicleId());
        Route route = this.routeService.getById(request.getRouteId());
        Coupon coupon = this.couponService.getByCode(request.getCouponCode());

        Booking booking = this.bookingMapper.toEntity(request, owner, vehicle, route, coupon);

        return this.bookingMapper.toResponse(this.bookingRepository.save(booking));
    }

    public void payWithCoupon(){}

    public void approveBooking(){}

    public void rejectBooking(){}

    public void assignPilot(){}

    public void myBookings(){}

    public void allBookings(){}

    public void bookingDetails(){}
}
