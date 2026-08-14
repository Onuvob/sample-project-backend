package com.sampleproject.modules.pilot.dto;

import com.sampleproject.common.enums.PilotStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PilotResponse {
    private Long id;
    private String name;
    private String phone;
    private PilotStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
