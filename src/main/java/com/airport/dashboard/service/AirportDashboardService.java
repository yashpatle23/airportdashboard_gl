package com.airport.dashboard.service;

import com.airport.dashboard.dto.response.DashboardRecordResponse;
import com.airport.dashboard.dto.response.DashboardSummaryResponse;
import com.airport.dashboard.enums.DashboardStatus;
import com.airport.dashboard.enums.EntityType;
import org.springframework.data.domain.Page;

public interface AirportDashboardService {

    DashboardSummaryResponse getDashboardSummary();

    Page<DashboardRecordResponse> getDashboardRecords(
            int page,
            int size,
            DashboardStatus status,
            EntityType entityType,
            String location,
            String search,
            String sortBy,
            String sortDirection
    );

}
