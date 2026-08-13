package com.sampleproject.modules.coupon.dto;

import com.sampleproject.common.enums.CouponStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CouponRequest {
    private String code;
    private Double amount;
    private LocalDate expiryDate;
    private CouponStatus status;
    private Long ownerId;
}
