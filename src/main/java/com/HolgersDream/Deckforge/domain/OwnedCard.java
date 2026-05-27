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
        if (userId < 0){
            throw new IllegalArgumentException("Et eget-kort skal have et gyldigt bruger id");
        }
        this.userId = userId;
        if (ownedCardId < 0){
            throw new IllegalArgumentException("Et eget-kort skal have et gyldigt eget-kort-id");
        }
        this.ownedCardId = ownedCardId;
        this.condition = condition;
        this.foil = foil;
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
}
