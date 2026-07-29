package com.airport.dashboard.converter;

import com.airport.dashboard.enums.DashboardStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DashboardStatusConverter
        implements AttributeConverter<DashboardStatus, String> {

    @Override
    public String convertToDatabaseColumn(DashboardStatus status) {
        return status == null ? null : status.getValue();
    }

    @Override
    public DashboardStatus convertToEntityAttribute(String dbValue) {

        if (dbValue == null) {
            return null;
        }

        for (DashboardStatus status : DashboardStatus.values()) {
            if (status.getValue().equals(dbValue)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown status: " + dbValue);
    }
}
