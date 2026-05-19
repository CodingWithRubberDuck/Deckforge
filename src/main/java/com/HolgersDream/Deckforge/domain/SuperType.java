package com.HolgersDream.Deckforge.domain;

public enum SuperType {
    BASIC("Basic"),
    LEGENDARY("Legendary");

    private String displayValue;

    private SuperType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
