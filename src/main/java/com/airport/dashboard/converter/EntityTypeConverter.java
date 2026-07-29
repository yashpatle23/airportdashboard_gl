package com.airport.dashboard.converter;

import com.airport.dashboard.enums.EntityType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EntityTypeConverter
        implements AttributeConverter<EntityType, String> {

    @Override
    public String convertToDatabaseColumn(EntityType type) {
        return type == null ? null : type.getValue();
    }

    @Override
    public EntityType convertToEntityAttribute(String dbValue) {

        if (dbValue == null) {
            return null;
        }

        for (EntityType type : EntityType.values()) {
            if (type.getValue().equals(dbValue)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown entity type: " + dbValue);
    }
}
