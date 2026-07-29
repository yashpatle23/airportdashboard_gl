package com.airport.dashboard.service.impl;





import com.airport.dashboard.dto.response.DashboardRecordResponse;
import com.airport.dashboard.dto.response.DashboardSummaryResponse;
import com.airport.dashboard.entity.AirportDashboard;
import com.airport.dashboard.enums.DashboardStatus;
import com.airport.dashboard.enums.EntityType;
import com.airport.dashboard.repository.AirportDashboardRepository;
import com.airport.dashboard.service.AirportDashboardService;
import com.airport.dashboard.specification.AirportDashboardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.airport.dashboard.cache.CacheNames;

@Service
@RequiredArgsConstructor
public class AirportDashboardServiceImpl implements AirportDashboardService {

    private final AirportDashboardRepository repository;

    @Override
//    @Cacheable(CacheNames.DASHBOARD_SUMMARY)
    public DashboardSummaryResponse getDashboardSummary() {

        DashboardSummaryResponse response = DashboardSummaryResponse.builder()
                .totalProject(0L)
                .pendingCalculation(0L)
                .pendingValidation(0L)
                .pendingVerification(0L)
                .underVerification(0L)
                .inMaintenance(0L)
                .committed(0L)
                .underClarification(0L)
                .escalatedToAtrs(0L)
                .blocked(0L)
                .active(0L)
                .build();

        repository.getStatusSummary().forEach(result -> {

            DashboardStatus status = (DashboardStatus) result[0];
            Long count = ((Number) result[1]).longValue();

            response.setTotalProject(response.getTotalProject() + count);

            switch (status) {

                case PENDING_CALCULATION ->
                        response.setPendingCalculation(count);

                case PENDING_VALIDATION ->
                        response.setPendingValidation(count);

                case PENDING_VERIFICATION ->
                        response.setPendingVerification(count);

                case UNDER_VERIFICATION ->
                        response.setUnderVerification(count);

                case IN_MAINTENANCE ->
                        response.setInMaintenance(count);

                case COMMITTED ->
                        response.setCommitted(count);

                case UNDER_CLARIFICATION ->
                        response.setUnderClarification(count);

                case ESCALATED_TO_ATRS ->
                        response.setEscalatedToAtrs(count);

                case BLOCKED ->
                        response.setBlocked(count);

                case ACTIVE ->
                        response.setActive(count);
            }

        });

        return response;

    }

    @Override
    public Page<DashboardRecordResponse> getDashboardRecords(
            int page,
            int size,
            DashboardStatus status,
            EntityType entityType,
            String location,
            String search,
            String sortBy,
            String sortDirection) {

        sortBy = switch (sortBy) {

            case "lastUpdated" -> "updatedAt";

            case "airport" -> "airportName";

            case "procedure" -> "procedureCode";

            default -> sortBy;
        };

        Sort sort = Sort.by(sortBy);

        sort = "desc".equalsIgnoreCase(sortDirection)
                ? sort.descending()
                : sort.ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<AirportDashboard> specification =
                Specification.where(AirportDashboardSpecification.notDeleted())
                        .and(AirportDashboardSpecification.hasStatus(status))
                        .and(AirportDashboardSpecification.hasEntityType(entityType))
                        .and(AirportDashboardSpecification.hasLocation(location))
                        .and(AirportDashboardSpecification.search(search));

        Page<AirportDashboard> dashboardPage =
                repository.findAll(specification, pageable);

        return dashboardPage.map(this::convertToResponse);

    }

    private DashboardRecordResponse convertToResponse(AirportDashboard airport) {

        return DashboardRecordResponse.builder()
                .entityType(airport.getEntityType().getValue())
                .id(airport.getEntityIdentifier())
                .icao(airport.getIcao())
                .iata(airport.getIata())
                .airport(airport.getAirportName())
                .location(airport.getLocation())
                .procedure(airport.getProcedureCode())
                .mits(airport.getMits())
                .effectiveDate(airport.getEffectiveDate())
                .status(airport.getStatus().getValue())
                .lastUpdated(airport.getUpdatedAt())
                .build();

    }

}
