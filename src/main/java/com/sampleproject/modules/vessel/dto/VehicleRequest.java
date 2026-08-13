package com.sampleproject.modules.vessel.dto;

import lombok.Data;

@Data
public class VehicleRequest {
    private String registrationNumber;
    private String name;
    private String type;
    private Double capacity;
}
