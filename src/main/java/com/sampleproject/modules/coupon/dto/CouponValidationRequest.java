package com.sampleproject.modules.coupon.dto;

import lombok.Data;

@Data
public class CouponValidationRequest {
    private String code;
    private Double routeFee;
}
