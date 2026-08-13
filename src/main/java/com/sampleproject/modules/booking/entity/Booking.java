package com.sampleproject.modules.booking.entity;

import com.sampleproject.common.entity.BaseEntity;
import com.sampleproject.common.enums.BookingStatus;
import com.sampleproject.common.enums.PaymentStatus;
import com.sampleproject.modules.coupon.entity.Coupon;
import com.sampleproject.modules.pilot.entity.Pilot;
import com.sampleproject.modules.route.entity.Route;
import com.sampleproject.modules.vessel.entity.Vehicle;
import com.sampleproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @ManyToOne
    @JoinColumn(name = "pilot_id")
    private Pilot pilot;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
