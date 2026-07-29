package com.airport.dashboard.enums;

public enum EntityType {

    AIRPORT("Airport"),
    AOM("AOM");

    private final String value;

    EntityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
