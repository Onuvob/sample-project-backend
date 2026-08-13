package com.sampleproject.modules.route.mapper;

import com.sampleproject.modules.route.dto.RouteRequest;
import com.sampleproject.modules.route.dto.RouteResponse;
import com.sampleproject.modules.route.entity.Route;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RouteMapper {

    public RouteResponse toResponse(Route route) {
        if (route == null) {
            return null;
        }

        RouteResponse response = new RouteResponse();

        response.setId(route.getId());
        response.setSource(route.getSource());
        response.setDestination(route.getDestination());
        response.setServiceFee(route.getServiceFee());
        response.setActive(route.getActive());

        return response;
    }

    public List<RouteResponse> toResponseList(List<Route> routes) {
        if (routes == null) {
            return Collections.emptyList();
        }

        return routes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Route toEntity(RouteRequest request) {
        if (request == null) {
            return null;
        }

        return Route.builder()
                .source(request.getSource())
                .destination(request.getDestination())
                .serviceFee(request.getServiceFee())
                .active(request.getActive() != null ? request.getActive() : Boolean.TRUE)
                .build();
    }

    public Route updateEntity(Route route, RouteRequest request) {
        if (route == null || request == null) {
            return null;
        }

        if (request.getSource() != null) {
            route.setSource(request.getSource());
        }

        if (request.getDestination() != null) {
            route.setDestination(request.getDestination());
        }

        if (request.getServiceFee() != null) {
            route.setServiceFee(request.getServiceFee());
        }

        if (request.getActive() != null) {
            route.setActive(request.getActive());
        }
        return route;
    }
}
