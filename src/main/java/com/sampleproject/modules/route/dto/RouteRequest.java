package com.sampleproject.modules.route.dto;

import lombok.Data;

@Data
public class RouteRequest {
    private String source;
    private String destination;
    private Double serviceFee;
    private Boolean active = Boolean.TRUE;
}
