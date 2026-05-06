package com.HolgersDream.Deckforge.domain;

public enum Role {
    USER("Bruger"),
    ORGANIZER("Arrangør"),
    ADMIN("Administrator");

    private final String displayValue;

    private Role(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
