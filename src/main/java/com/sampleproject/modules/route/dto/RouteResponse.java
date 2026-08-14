package com.sampleproject.modules.route.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {
    private Long id;
    private String source;
    private String destination;
    private Double serviceFee;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
