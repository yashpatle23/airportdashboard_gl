package com.airport.dashboard.converter;

import com.airport.dashboard.enums.EntityType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Converter(autoApply = true)
public class EntityTypeConverter
        implements AttributeConverter<EntityType, String> {

    private static final Map<String, EntityType> LOOKUP = buildLookup();

    private static Map<String, EntityType> buildLookup() {
        Map<String, EntityType> map = new HashMap<>();
        for (EntityType type : EntityType.values()) {
            String nameKey = normalize(type.name());
            String valueKey = normalize(type.getValue());
            map.put(nameKey, type);
            map.put(valueKey, type);
            map.put(compact(nameKey), type);
            map.put(compact(valueKey), type);
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

        return normalized.replaceAll("_+", "_");
    }

    private static String compact(String value) {
        return value == null ? null : value.replaceAll("[^A-Z0-9]", "");
    }

    private static EntityType guessByPattern(String compactValue) {
        if (compactValue == null || compactValue.isBlank()) {
            return null;
        }
        if (compactValue.contains("AIRPORT")) return EntityType.AIRPORT;
        if (compactValue.contains("AOM")) return EntityType.AOM;
        return null;
    }

    @Override
    public String convertToDatabaseColumn(EntityType type) {
        return type == null ? null : type.getValue();
    }

    @Override
    public EntityType convertToEntityAttribute(String dbValue) {

        if (dbValue == null) {
            return null;
        }

        String normalized = normalize(dbValue);
        String compactValue = compact(normalized);

        EntityType type = LOOKUP.get(normalized);
        if (type == null) {
            type = LOOKUP.get(compactValue);
        }
        if (type == null) {
            type = guessByPattern(compactValue);
        }
        if (type != null) {
            return type;
        }

        throw new IllegalArgumentException("Unknown entity type: " + dbValue + " (normalized=" + normalized + ")");
    }
}
