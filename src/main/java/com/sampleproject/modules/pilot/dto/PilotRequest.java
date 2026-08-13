package com.sampleproject.modules.pilot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PilotRequest {
    @NotBlank(message = "Name must not be blank")
    private String name;
    @NotBlank(message = "Phone must not be blank")
    private String phone;
    @NotBlank(message = "Status must not be blank")
    private String status;
}
