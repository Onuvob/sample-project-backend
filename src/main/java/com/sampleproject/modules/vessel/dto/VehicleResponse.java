package com.sampleproject.modules.vessel.dto;

import com.sampleproject.common.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private Long id;
    private String registrationNumber;
    private String name;
    private String type;
    private Double capacity;
    private String ownerFirstName;
    private String ownerLastName;
    private VehicleStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
