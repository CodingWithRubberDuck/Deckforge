package com.HolgersDream.Deckforge.domain;

import java.text.Normalizer;

public class Deck {
    private int deckId;
    private int cardAmount;
    private Format format;

    public Deck() {
    }

    public Deck(int deckId, int cardAmount, Format format) {
        this.deckId = deckId;
        this.cardAmount = cardAmount;
        this.format = format;
    }

    /// Getters
    public int getDeckId() {
        return deckId;
    }

    public int getCardAmount() {
        return cardAmount;
    }

    public Format getFormat() {
        return format;
    }

    /// Setters
    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public void setCardAmount(int cardAmount) {
        this.cardAmount = cardAmount;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
}
