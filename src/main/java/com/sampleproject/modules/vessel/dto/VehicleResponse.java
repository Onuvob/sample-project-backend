package com.sampleproject.modules.vessel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
