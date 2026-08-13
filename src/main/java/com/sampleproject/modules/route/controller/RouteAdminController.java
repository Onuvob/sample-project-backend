package com.sampleproject.modules.route.controller;

import com.sampleproject.modules.route.dto.RouteRequest;
import com.sampleproject.modules.route.dto.RouteResponse;
import com.sampleproject.modules.route.service.RouteService;
import com.sampleproject.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminRoute")
@RequiredArgsConstructor
public class RouteAdminController {

    private final RouteService routeService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RouteResponse>> createRoutes(@Valid @RequestBody RouteRequest request){
        try {
            RouteResponse routeResponse = this.routeService.createRoute(request);
            return ResponseEntity.ok(ApiResponse.success("Route created successfully", routeResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<RouteResponse>> updateRoute(@PathVariable Long id, @Valid @RequestBody RouteRequest request){
        try {
            RouteResponse routeResponse = this.routeService.updateRoute(id, request);
            return ResponseEntity.ok(ApiResponse.success("Route updated successfully", routeResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRoute(@PathVariable Long id){
        try {
            this.routeService.deleteRoute(id);
            return ResponseEntity.ok(ApiResponse.success("Route deleted successfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
