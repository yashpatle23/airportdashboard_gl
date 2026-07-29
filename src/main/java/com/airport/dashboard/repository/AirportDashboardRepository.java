package com.airport.dashboard.repository;

import com.airport.dashboard.entity.AirportDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportDashboardRepository extends
        JpaRepository<AirportDashboard, Long>,
        JpaSpecificationExecutor<AirportDashboard> {

    @Query("""
            SELECT a.status, COUNT(a)
            FROM AirportDashboard a
            WHERE a.deleted = false
            GROUP BY a.status
            """)
    List<Object[]> getStatusSummary();

}
