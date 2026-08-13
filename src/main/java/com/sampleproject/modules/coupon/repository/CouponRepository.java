package com.sampleproject.modules.coupon.repository;

import com.sampleproject.modules.coupon.dto.CouponResponse;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT new com.sampleproject.modules.coupon.dto.CouponResponse() " +
            "FROM Coupon c WHERE (:code IS NULL OR LOWER(c.code) LIKE :code)")
    Page<CouponResponse> getPaginatedList(@Param("code") String code, Pageable pageable);

    @Query("SELECT new com.sampleproject.modules.coupon.dto.CouponResponse() " +
            "FROM Coupon c WHERE c.owner = :currentUser " +
            "AND (:code IS NULL OR LOWER(c.code) LIKE :code)")
    Page<CouponResponse> getSelfPaginatedList(@Param("code") String code,
                                              @Param("currentUser") User currentUser,
                                              Pageable pageable);

    Optional<Coupon> findByCode(String code);
}
