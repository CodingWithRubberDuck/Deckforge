package com.HolgersDream.Deckforge.domain;

public enum Condition {
    MINT("Mint"),
    NEAR_MINT("Near Mint"),
    EXCELLENT("Excellent"),
    GOOD("Good"),
    LIGHT_PLAYED("Light Played"),
    PLAYED("Played"),
    POOR("Poor"),
    UNSPECIFIED("Unspecified");

    private String displayValue;

    private Condition(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
