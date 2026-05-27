package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.Format;

public class DeckRequest {
    private int deckId;
    private int userId;
    private int cardAmount;
    private String deckName;
    private Format format;


    public DeckRequest() {
    }

    //Getters
    public int getDeckId() {
        return deckId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCardAmount() {
        return cardAmount;
    }

    public String getDeckName(){
        return deckName;
    }

    public Format getFormat() {
        return format;
    }


    //Setters

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

}
