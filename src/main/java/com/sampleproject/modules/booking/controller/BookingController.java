package com.sampleproject.modules.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    @PostMapping("/create")
    public void createBooking(){

    }

    @GetMapping("/list")
    public void getBookings(){

    }

    @GetMapping("/get/{id}")
    public void getBooking(@PathVariable Long id){

    }

    @PostMapping("/pay/{id}")
    public void assignPilot(@PathVariable Long id){

    }
}
