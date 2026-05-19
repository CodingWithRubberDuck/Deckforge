package com.HolgersDream.Deckforge.domain;

public enum Rarity {
    COMMON("Common"),
    UNCOMMON("Uncommon"),
    RARE("Rare"),
    MYTHIC_RARE("Mythic Rare"),
    LAND("Land");

    private String displayValue;

    private Rarity(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
