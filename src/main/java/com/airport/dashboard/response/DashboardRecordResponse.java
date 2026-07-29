package com.airport.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardRecordResponse {

    private String entityType;

    private String id;

    private String icao;

    private String iata;

    private String airport;

    private String location;

    private String procedure;

    private String mits;

    private LocalDate effectiveDate;

    private String status;

    private LocalDateTime lastUpdated;

}
