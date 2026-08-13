package com.sampleproject.modules.pilot.entity;

import com.sampleproject.common.entity.BaseEntity;
import com.sampleproject.common.enums.PilotStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pilots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pilot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    @Enumerated(EnumType.STRING)
    private PilotStatus status;
}
