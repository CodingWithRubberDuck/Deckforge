package com.HolgersDream.Deckforge.domain;

public enum Type {
    ARTIFACT("Artifact"),
    CREATURE("Creature"),
    ENCHANTMENT("Enchantment"),
    INSTANT("Instant"),
    KINDRED("Kindred"),
    LAND("Land"),
    SORCERY("Sorcery");

    private final String displayValue;

    private Type(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
