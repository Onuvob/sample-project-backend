package com.sampleproject.modules.booking.controller;

import com.sampleproject.modules.booking.dto.AssignPilotRequest;
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
@RequestMapping("/adminBookings")
@RequiredArgsConstructor
public class BookingAdminController {

    private final BookingService bookingService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getBookings(RequestUtil request){

        Page<BookingResponse> data = this.bookingService.allBookings(request);
        Map<String, Object> response = new HashMap<>();

        response.put("objectList", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Bookings retrieve successfully", response));

    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> approve(@PathVariable Long id){
        try{
            BookingResponse bookingResponse = this.bookingService.approveBooking(id);
            return ResponseEntity.ok(ApiResponse.success("Booking approved successfully", bookingResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> reject(@PathVariable Long id){
        try{
            BookingResponse bookingResponse = this.bookingService.rejectBooking(id);
            return ResponseEntity.ok(ApiResponse.success("Booking rejected successfully", bookingResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/assignPilot")
    public ResponseEntity<ApiResponse<BookingResponse>> assignPilot(@Valid @RequestBody AssignPilotRequest request){
        try{
            BookingResponse bookingResponse = this.bookingService.assignPilot(request);
            return ResponseEntity.ok(ApiResponse.success("Pilot assigned successfully", bookingResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
