package com.sampleproject.modules.pilot.controller;

import com.sampleproject.modules.pilot.dto.PilotRequest;
import com.sampleproject.modules.pilot.dto.PilotResponse;
import com.sampleproject.modules.pilot.service.PilotService;
import com.sampleproject.util.ApiResponse;
import com.sampleproject.util.RequestUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminPilots")
@RequiredArgsConstructor
public class PilotAdminController {

    private final PilotService pilotService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getPilots(RequestUtil request){

        Page<PilotResponse> data = this.pilotService.getPaginatedList(request);
        Map<String, Object> response = new HashMap<>();

        response.put("objectList", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Pilots retrieve successfully", response));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PilotResponse>> createPilot(@Valid @RequestBody PilotRequest request){
        try{
            PilotResponse pilotResponse = this.pilotService.createPilot(request);
            return ResponseEntity.ok(ApiResponse.success("Pilot created successfully", pilotResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PilotResponse>> updatePilot(@PathVariable Long id, @RequestBody PilotRequest request){
        try {
            PilotResponse pilotResponse = this.pilotService.updatePilot(id, request);
            return ResponseEntity.ok(ApiResponse.success("Pilot updated successfully", pilotResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deletePilot(@PathVariable Long id){
        try {
            this.pilotService.deletePilot(id);
            return ResponseEntity.ok(ApiResponse.success("Pilot deleted successfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
