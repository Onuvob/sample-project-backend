package com.sampleproject.modules.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminBooking")
@RequiredArgsConstructor
public class BookingAdminController {

    @GetMapping("/list")
    public void getBookings(){

    }

    @PutMapping("/approve/{id}")
    public void approve(){

    }

    @PutMapping("/reject/{id}")
    public void reject(){

    }

    @PutMapping("/assignPilot")
    public void assignPilot(){

    }
}
