package com.airport.dashboard.entity;

import com.airport.dashboard.enums.DashboardStatus;
import com.airport.dashboard.enums.EntityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "airport_dashboard")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_identifier", nullable = false, unique = true)
    private String entityIdentifier;

    @Column(name = "entity_type", nullable = false)
    private EntityType entityType;

    @Column(name = "icao", nullable = false, columnDefinition = "CHAR(4)")
    private String icao;

    @Column(name = "iata", columnDefinition = "CHAR(3)")
    private String iata;

    @Column(name = "airport_name", nullable = false)
    private String airportName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "procedure_code")
    private String procedureCode;

    @Column(name = "mits")
    private String mits;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "status", nullable = false)
    private DashboardStatus status;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (deleted == null) {
            deleted = false;
        }

        if (createdBy == null) {
            createdBy = "SYSTEM";
        }

        if (updatedBy == null) {
            updatedBy = "SYSTEM";
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
