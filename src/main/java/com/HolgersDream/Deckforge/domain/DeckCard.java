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
        if (deckId < 0){
            throw new IllegalArgumentException("Et deck-kort skal have et gyldigt deck-id");
        }
        this.deckId = deckId;
        if (deckContainId < 0){
            throw new IllegalArgumentException("Et deck-kort skal have et gyldigt deck-indeholder-id");
        }
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
