package com.sampleproject.modules.pilot.dto;

import com.sampleproject.common.enums.PilotStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PilotResponse {
    private Long id;
    private String name;
    private String phone;
    private PilotStatus status;
}
