package com.sampleproject.modules.booking.controller;

import com.sampleproject.modules.booking.dto.AssignPilotRequest;
import com.sampleproject.modules.booking.dto.BookingResponse;
import com.sampleproject.modules.booking.service.BookingService;
import com.sampleproject.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminBooking")
@RequiredArgsConstructor
public class BookingAdminController {

    private final BookingService bookingService;

    @GetMapping("/list")
    public void getBookings(){

    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> approve(@PathVariable Long id){


    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> reject(@PathVariable Long id){

    }

    @PutMapping("/assignPilot")
    public ResponseEntity<ApiResponse<BookingResponse>> assignPilot(@Valid @RequestBody AssignPilotRequest request){

    }
}
