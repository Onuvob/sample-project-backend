package com.sampleproject.modules.vessel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VehicleRequest {
    @NotBlank(message = "Registration number must not be blank")
    private String registrationNumber;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Type must not be blank")
    private String type;
    @NotNull(message = "Capacity must not be null")
    @Positive(message = "Capacity must be greater than zero")
    private Double capacity;
}
