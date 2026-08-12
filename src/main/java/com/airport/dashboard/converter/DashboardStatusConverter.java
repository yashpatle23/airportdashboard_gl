package com.airport.dashboard.converter;

import com.airport.dashboard.enums.DashboardStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Converter(autoApply = true)
public class DashboardStatusConverter
        implements AttributeConverter<DashboardStatus, String> {

    private static final Map<String, DashboardStatus> LOOKUP = buildLookup();

    private static Map<String, DashboardStatus> buildLookup() {
        Map<String, DashboardStatus> map = new HashMap<>();
        for (DashboardStatus status : DashboardStatus.values()) {
            String nameKey = normalize(status.name());
            String valueKey = normalize(status.getValue());
            map.put(nameKey, status);
            map.put(valueKey, status);
            map.put(compact(nameKey), status);
            map.put(compact(valueKey), status);
        }
        return map;
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT)
                .replace('-', '_')
                .replace(' ', '_')
                .replace('/', '_')
                .replace('.', '_');

        // Collapse repeated separators to handle data like "PENDING__CALCULATION".
        return normalized.replaceAll("_+", "_");
    }

    private static String compact(String value) {
        return value == null ? null : value.replaceAll("[^A-Z0-9]", "");
    }

    private static DashboardStatus guessByPattern(String compactValue) {
        if (compactValue == null || compactValue.isBlank()) {
            return null;
        }

        if (compactValue.contains("PENDINGCALC")) return DashboardStatus.PENDING_CALCULATION;
        if (compactValue.contains("PENDINGVALID")) return DashboardStatus.PENDING_VALIDATION;
        if (compactValue.contains("PENDINGVERIF")) return DashboardStatus.PENDING_VERIFICATION;
        if (compactValue.contains("UNDERVERIF")) return DashboardStatus.UNDER_VERIFICATION;
        if (compactValue.contains("MAINT")) return DashboardStatus.IN_MAINTENANCE;
        if (compactValue.contains("COMMIT")) return DashboardStatus.COMMITTED;
        if (compactValue.contains("CLARIF")) return DashboardStatus.UNDER_CLARIFICATION;
        if (compactValue.contains("ESCALAT")) return DashboardStatus.ESCALATED_TO_ATRS;
        if (compactValue.contains("BLOCK")) return DashboardStatus.BLOCKED;
        if (compactValue.contains("ACTIVE")) return DashboardStatus.ACTIVE;

        return null;
    }

    @Override
    public String convertToDatabaseColumn(DashboardStatus status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public DashboardStatus convertToEntityAttribute(String dbValue) {

        if (dbValue == null) {
            return null;
        }

        String normalized = normalize(dbValue);
        String compactValue = compact(normalized);

        DashboardStatus status = LOOKUP.get(normalized);
        if (status == null) {
            status = LOOKUP.get(compactValue);
        }
        if (status == null) {
            status = guessByPattern(compactValue);
        }
        if (status != null) {
            return status;
        }

        throw new IllegalArgumentException("Unknown status: " + dbValue + " (normalized=" + normalized + ")");
    }
}
