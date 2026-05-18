package com.HolgersDream.Deckforge.domain;

public enum Format {
    COMMANDER("Commander"),
    STANDARD("Standard");

    private final String displayValue;

    private Format(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
