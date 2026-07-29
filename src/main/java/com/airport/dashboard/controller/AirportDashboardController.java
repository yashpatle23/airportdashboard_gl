package com.airport.dashboard.controller;

import com.airport.dashboard.dto.response.DashboardRecordResponse;
import com.airport.dashboard.dto.response.DashboardSummaryResponse;
import com.airport.dashboard.enums.DashboardStatus;
import com.airport.dashboard.enums.EntityType;
import com.airport.dashboard.service.AirportDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.airport.dashboard.dto.common.ApiResponse;
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AirportDashboardController {

    private final AirportDashboardService dashboardService;

    @GetMapping("/summary")
    public ApiResponse<DashboardSummaryResponse> getDashboardSummary() {

        return ApiResponse.<DashboardSummaryResponse>builder()
                .success(true)
                .message("Dashboard summary fetched successfully")
                .data(dashboardService.getDashboardSummary())
                .build();

    }

    @GetMapping("/records")
    public ApiResponse<Page<DashboardRecordResponse>> getDashboardRecords(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(required = false)
            DashboardStatus status,

            @RequestParam(required = false)
            EntityType entityType,

            @RequestParam(required = false)
            String location,

            @RequestParam(required = false)
            String search,

            @RequestParam(defaultValue = "updatedAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDirection) {

        Page<DashboardRecordResponse> records =
                dashboardService.getDashboardRecords(
                        page,
                        size,
                        status,
                        entityType,
                        location,
                        search,
                        sortBy,
                        sortDirection
                );

        return ApiResponse.<Page<DashboardRecordResponse>>builder()
                .success(true)
                .message("Dashboard records fetched successfully")
                .data(records)
                .build();

    }

}
