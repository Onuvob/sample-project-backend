package com.sampleproject.modules.route.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {
    private Long id;
    private String source;
    private String destination;
    private Double serviceFee;
    private Boolean active;
}
