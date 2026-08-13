package com.sampleproject.modules.vessel.controller;

import com.sampleproject.modules.vessel.dto.VehicleResponse;
import com.sampleproject.modules.vessel.service.VehicleService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/adminVehicles")
@RequiredArgsConstructor
public class VehicleAdminController {

    private final VehicleService vehicleService;

    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse<String>> approveVehicle(@PathVariable Long id){
        try {
            this.vehicleService.approveVehicle(id);
            return ResponseEntity.ok(ApiResponse.success("Vehicle approved successfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse<String>> rejectVehicle(@PathVariable Long id){
        try {
            this.vehicleService.rejectVehicle(id);
            return ResponseEntity.ok(ApiResponse.success("Vehicle rejected successfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<?>> getPendingVehicles(RequestUtil request){
        Page<VehicleResponse> data = this.vehicleService.getPendingVehicles(request);
        Map<String, Object> response = new HashMap<>();

        response.put("objectList", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Pending vehicle retrieve successfully", response));
    }
}
