package com.sampleproject.modules.booking.service;

import com.sampleproject.common.enums.BookingStatus;
import com.sampleproject.modules.booking.dto.AssignPilotRequest;
import com.sampleproject.modules.booking.dto.BookingRequest;
import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.entity.Booking;
import com.sampleproject.modules.booking.mapper.BookingMapper;
import com.sampleproject.modules.booking.repository.BookingRepository;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.modules.coupon.service.CouponService;
import com.sampleproject.modules.pilot.entity.Pilot;
import com.sampleproject.modules.pilot.service.PilotService;
import com.sampleproject.modules.route.entity.Route;
import com.sampleproject.modules.route.service.RouteService;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.modules.vessel.service.VehicleService;
import com.sampleproject.user.entity.User;
import com.sampleproject.util.CurrentUserService;
import com.sampleproject.util.QueryHelper;
import com.sampleproject.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public BookingResponse approveBooking(Long id){
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found with id: "+id));
        booking.setBookingStatus(BookingStatus.APPROVED);
        return this.bookingMapper.toResponse(this.bookingRepository.save(booking));
    }

    public BookingResponse rejectBooking(Long id){
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found with id: "+id));
        booking.setBookingStatus(BookingStatus.REJECTED);
        return this.bookingMapper.toResponse(this.bookingRepository.save(booking));
    }

    public BookingResponse assignPilot(AssignPilotRequest request){
        Booking booking = this.bookingRepository.findById(request.getBookingId())
                .orElseThrow(()-> new RuntimeException("Booking not found with id: "+request.getBookingId()));

        Pilot pilot = this.pilotService.getById(request.getPilotId());
        booking.setPilot(pilot);

        return this.bookingMapper.toResponse(this.bookingRepository.save(booking));
    }

    public Page<BookingResponse> myBookings(RequestUtil request){
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        User owner = this.currentUserService.getCurrentUser();

        return this.bookingRepository.getSelfPaginatedList(owner, pageable);
    }

    public Page<BookingResponse> allBookings(RequestUtil request){
        Sort sort = Sort.by(request.getSortDir().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC, request.getSortField());
        Pageable pageable = PageRequest.of(request.getPageNum() - 1, request.getPageSize(), sort);

        return this.bookingRepository.getPaginatedList(pageable);
    }

    public BookingResponse bookingDetails(Long id){
        Booking booking = this.bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found with id: "+id));
        return this.bookingMapper.toResponse(booking);
    }
}
