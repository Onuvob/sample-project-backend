package com.sampleproject.modules.vessel.entity;

import com.sampleproject.common.entity.BaseEntity;
import com.sampleproject.common.enums.VehicleStatus;
import com.sampleproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationNumber;
    private String name;
    private String type;
    private Double capacity;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
}
