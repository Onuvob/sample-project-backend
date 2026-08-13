package com.sampleproject.modules.coupon.entity;

import com.sampleproject.common.entity.BaseEntity;
import com.sampleproject.common.enums.CouponStatus;
import com.sampleproject.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private Double amount;
    private LocalDate expiryDate;
    @Enumerated(EnumType.STRING)
    private CouponStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
}
