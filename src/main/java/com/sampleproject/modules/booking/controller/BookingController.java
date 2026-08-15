package com.sampleproject.modules.booking.controller;

import com.sampleproject.modules.booking.dto.BookingRequest;
import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.service.BookingService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.RequestUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@Valid @RequestBody BookingRequest request){
        try{
            BookingResponse bookingResponse = this.bookingService.createBooking(request);
            return ResponseEntity.ok(ApiResponse.success("Booking created successfully", bookingResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getBookings(RequestUtil request){
        Page<BookingResponse> data = this.bookingService.myBookings(request);
        Map<String, Object> response = new HashMap<>();

        response.put("data", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Bookings retrieve successfully", response));

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBooking(@PathVariable Long id){
        try{
            BookingResponse bookingResponse = this.bookingService.bookingDetails(id);
            return ResponseEntity.ok(ApiResponse.success("Booking retrieve successfully", bookingResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
