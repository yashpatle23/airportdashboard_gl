package com.airport.dashboard.enums;

public enum DashboardStatus {

    PENDING_CALCULATION("Pending Calculation"),

    PENDING_VALIDATION("Pending Validation"),

    PENDING_VERIFICATION("Pending Verification"),

    UNDER_VERIFICATION("Under Verification"),

    IN_MAINTENANCE("In Maintenance"),

    COMMITTED("Committed"),

    UNDER_CLARIFICATION("Under Clarification"),

    ESCALATED_TO_ATRS("Escalated to ATRS"),

    BLOCKED("Blocked"),

    ACTIVE("Active");

    private final String value;

    DashboardStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
