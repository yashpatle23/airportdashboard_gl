package com.airport.dashboard.specification;

import com.airport.dashboard.entity.AirportDashboard;
import com.airport.dashboard.enums.DashboardStatus;
import com.airport.dashboard.enums.EntityType;
import org.springframework.data.jpa.domain.Specification;

public class AirportDashboardSpecification {

    private AirportDashboardSpecification() {
    }

    public static Specification<AirportDashboard> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    public static Specification<AirportDashboard> hasStatus(DashboardStatus status) {

        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.equal(root.get("status"), status);
    }

    public static Specification<AirportDashboard> hasEntityType(EntityType entityType) {

        return (root, query, cb) ->
                entityType == null
                        ? cb.conjunction()
                        : cb.equal(root.get("entityType"), entityType);
    }

    public static Specification<AirportDashboard> hasLocation(String location) {

        return (root, query, cb) ->

                location == null || location.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("location")),
                        "%" + location.toLowerCase() + "%");
    }

    public static Specification<AirportDashboard> search(String keyword) {

        return (root, query, cb) -> {

            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }

            String search = "%" + keyword.toLowerCase() + "%";

            return cb.or(

                    cb.like(cb.lower(root.get("entityIdentifier")), search),

                    cb.like(cb.lower(root.get("airportName")), search),

                    cb.like(cb.lower(root.get("icao")), search),

                    cb.like(cb.lower(root.get("iata")), search),

                    cb.like(cb.lower(root.get("location")), search),

                    cb.like(cb.lower(root.get("procedureCode")), search),

                    cb.like(cb.lower(root.get("mits")), search)

            );

        };

    }

}
