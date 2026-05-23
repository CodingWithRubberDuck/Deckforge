package com.HolgersDream.Deckforge.domain;

public class DeckCard extends Card{
    private int deckId;
    private int deckContainId;
    private boolean isCommander;


    public DeckCard(int cardId, int blackMana, int blueMana, int greenMana, int redMana, int whiteMana,
                     int neutralMana, String name, SuperType superType, Type type, Type multiType,
                     String subType, boolean canBeCommander, String picture, String setName, String ruleText,
                     int toughness, int power, Rarity rarity, int deckId, int deckContainId, boolean isCommander) {
        super(cardId, blackMana, blueMana, greenMana, redMana, whiteMana,
                neutralMana, name, superType, type, multiType, subType, canBeCommander, picture, setName, ruleText, toughness, power, rarity);
        this.deckId = deckId;
        this.deckContainId = deckContainId;
        this.isCommander = isCommander;
    }


    /// Getters
    public int getDeckId() {
        return deckId;
    }

    public int getDeckContainId() {
        return deckContainId;
    }

    public boolean isCommander() {
        return isCommander;
    }
}
