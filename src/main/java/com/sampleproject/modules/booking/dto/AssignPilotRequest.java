package com.sampleproject.modules.booking.dto;

import lombok.Data;

@Data
public class AssignPilotRequest {
    private Long pilotId;
    private Long bookingId;
}
