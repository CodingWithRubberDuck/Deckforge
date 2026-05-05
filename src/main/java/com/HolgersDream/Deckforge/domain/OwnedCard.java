package com.HolgersDream.Deckforge.domain;

public class OwnedCard extends Card {
    private int ownedCardId;
    private String condition;
    private String foil;


    public OwnedCard() {
        super();
    }

    public OwnedCard(int cardId, int blackMana, int blueMana, int greenMana, int redMana, int whiteMana,
                     int neutralMana, String name, Type type, String picture, String setName, String ruleText,
                     int toughness, int power, Rarity rarity, int ownedCardId, String condition, String foil) {
        super(cardId, blackMana, blueMana, greenMana, redMana, whiteMana,
                neutralMana, name, type, picture, setName, ruleText, toughness, power, rarity);
        this.ownedCardId = ownedCardId;
        this.condition = condition;
        this.foil = foil;
    }

    /// Getters
    public int getOwnedCardId() {
        return ownedCardId;
    }

    public String getCondition() {
        return condition;
    }

    public String getFoil() {
        return foil;
    }

    /// Setters
    public void setOwnedCardId(int ownedCardId) {
        this.ownedCardId = ownedCardId;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setFoil(String foil) {
        this.foil = foil;
    }
}
