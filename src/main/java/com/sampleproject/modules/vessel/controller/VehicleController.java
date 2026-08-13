package com.sampleproject.modules.vessel.controller;


import com.sampleproject.modules.vessel.dto.VehicleRequest;
import com.sampleproject.modules.vessel.dto.VehicleResponse;
import com.sampleproject.modules.vessel.service.VehicleService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.RequestUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(@Valid @RequestBody VehicleRequest request){
        try{
            VehicleResponse vehicleResponse = this.vehicleService.createVehicle(request);
            return ResponseEntity.ok(ApiResponse.success("Vehicle created successfully", vehicleResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequest request){
        try {
            VehicleResponse vehicleResponse = this.vehicleService.updateVehicle(id, request);
            return ResponseEntity.ok(ApiResponse.success("Vehicle updated successfully", vehicleResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVehicle(@PathVariable Long id){
        try {
            this.vehicleService.deleteVehicle(id);
            return ResponseEntity.ok(ApiResponse.success("Vehicle deleted successfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/selfList")
    public ResponseEntity<ApiResponse<?>> getMyVehicles(RequestUtil request){
        Page<VehicleResponse> data = this.vehicleService.getSelfPaginatedList(request);
        Map<String, Object> response = new HashMap<>();

        response.put("objectList", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Vehicle retrieve successfully", response));
    }
}
