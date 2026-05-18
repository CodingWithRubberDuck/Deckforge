package com.HolgersDream.Deckforge.domain;

public class OwnedCard extends Card {
    private int ownedCardId;
    private int userId;
    private Condition condition;
    private String foil;


    public OwnedCard() {
        super();
    }

    public OwnedCard(int cardId, int blackMana, int blueMana, int greenMana, int redMana, int whiteMana,
                     int neutralMana, String name, SuperType superType, Type type, Type multiType,
                     String subType, boolean canBeCommander, String picture, String setName, String ruleText,
                     int toughness, int power, Rarity rarity, int ownedCardId, int userId, Condition condition, String foil) {
        super(cardId, blackMana, blueMana, greenMana, redMana, whiteMana,
                neutralMana, name, superType, type, multiType, subType, canBeCommander, picture, setName, ruleText, toughness, power, rarity);
        this.userId = userId;
        this.ownedCardId = ownedCardId;
        this.condition = condition;
        this.foil = foil;
    }

    //Constructor til at vise billeder af ejede kort i samling
    public OwnedCard(int ownedCardId, String picture) {
        super();
        this.ownedCardId = ownedCardId;
        this.setPicture(picture);
    }

    /// Getters
    public int getOwnedCardId() {
        return ownedCardId;
    }

    public int getUserId() {
        return userId;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getFoil() {
        return foil;
    }

    /// Setters
    public void setOwnedCardId(int ownedCardId) {
        this.ownedCardId = ownedCardId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setFoil(String foil) {
        this.foil = foil;
    }
}
