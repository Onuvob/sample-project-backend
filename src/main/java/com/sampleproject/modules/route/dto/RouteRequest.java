package com.sampleproject.modules.route.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RouteRequest {
    @NotBlank(message = "Source must not be blank")
    private String source;
    @NotBlank(message = "Destination must not be blank")
    private String destination;
    @NotNull(message = "Service fee must not be null")
    @Positive(message = "Service fee be greater than zero")
    private Double serviceFee;
    private Boolean active = Boolean.TRUE;
}
