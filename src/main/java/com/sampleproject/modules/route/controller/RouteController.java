package com.sampleproject.modules.route.controller;

import com.sampleproject.modules.route.dto.RouteResponse;
import com.sampleproject.modules.route.service.RouteService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getRoutes(RequestUtil request){
        Page<RouteResponse> data = this.routeService.getPaginatedList(request);
        Map<String, Object> response = new HashMap<>();

        response.put("data", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Route retrieve successfully", response));

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<RouteResponse>> getRoute(@PathVariable Long id){
        try {
            RouteResponse routeResponse = this.routeService.getRoute(id);
            return ResponseEntity.ok(ApiResponse.success("Route retrieve successfully", routeResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
