package com.sampleproject.modules.coupon.controller;

import com.sampleproject.modules.coupon.dto.CouponRequest;
import com.sampleproject.modules.coupon.dto.CouponResponse;
import com.sampleproject.modules.coupon.service.CouponService;
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
@RequestMapping("/api/adminCoupons")
@RequiredArgsConstructor
public class CouponAdminController {

    private final CouponService couponService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CouponResponse>> createCoupon(@Valid @RequestBody CouponRequest request){
        try{
            CouponResponse couponResponse = this.couponService.createCoupon(request);
            return ResponseEntity.ok(ApiResponse.success("Coupon created successfully", couponResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<CouponResponse>> getCoupon(@PathVariable Long id){
        try{
            CouponResponse couponResponse = this.couponService.getCoupon(id);
            return ResponseEntity.ok(ApiResponse.success("Coupon retrieve successfully", couponResponse));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }

    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getCoupons(RequestUtil request){

        Page<CouponResponse> data = this.couponService.getPaginatedList(request);
        Map<String, Object> response = new HashMap<>();

        response.put("data", data.getContent());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        response.put("totalItems", data.getTotalElements());

        response.put("sortField", request.getSortField());
        response.put("sortDir", request.getSortDir());
        response.put("reverseSortDir", (request.getSortDir().equals("asc") ? "desc" : "asc"));
        return ResponseEntity.ok(ApiResponse.success("Pilots retrieve successfully", response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCoupon(@PathVariable Long id){
        try {
            this.couponService.deleteCoupon(id);
            return ResponseEntity.ok(ApiResponse.success("Coupon deleted successfully", null));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}
