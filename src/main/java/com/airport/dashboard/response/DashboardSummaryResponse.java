package com.airport.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    private Long totalProject;

    private Long pendingCalculation;

    private Long pendingValidation;

    private Long pendingVerification;

    private Long underVerification;

    private Long inMaintenance;

    private Long committed;

    private Long underClarification;

    private Long escalatedToAtrs;

    private Long blocked;

    private Long active;

}
