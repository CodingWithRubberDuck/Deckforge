package com.HolgersDream.Deckforge.domain;

public class Card {
    private int cardId;
    private int blackMana;
    private int blueMana;
    private int greenMana;
    private int redMana;
    private int whiteMana;
    private int neutralMana;
    private String name;
    private Type type;
    private String picture;
    private String setName;
    private String ruleText;
    private int toughness;
    private int power;
    private Rarity rarity;

    public Card() {
    }

    public Card(int cardId, int blackMana, int blueMana, int greenMana, int redMana, int whiteMana,
                int neutralMana, String name, Type type, String picture, String setName, String ruleText,
                int toughness, int power, Rarity rarity) {
        this.cardId = cardId;
        this.blackMana = blackMana;
        this.blueMana = blueMana;
        this.greenMana = greenMana;
        this.redMana = redMana;
        this.whiteMana = whiteMana;
        this.neutralMana = neutralMana;
        this.name = name;
        this.type = type;
        this.picture = picture;
        this.setName = setName;
        this.ruleText = ruleText;
        this.toughness = toughness;
        this.power = power;
        this.rarity = rarity;
    }

    /// Getters
    public int getCardId() {
        return cardId;
    }

    public int getBlackMana() {
        return blackMana;
    }

    public int getBlueMana() {
        return blueMana;
    }

    public int getGreenMana() {
        return greenMana;
    }

    public int getRedMana() {
        return redMana;
    }

    public int getWhiteMana() {
        return whiteMana;
    }

    public int getNeutralMana() {
        return neutralMana;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getPicture() {
        return picture;
    }

    public String getSetName() {
        return setName;
    }

    public String getRuleText() {
        return ruleText;
    }

    public int getToughness() {
        return toughness;
    }

    public int getPower() {
        return power;
    }

    public Rarity getRarity() {
        return rarity;
    }

    /// Setters
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public void setBlackMana(int blackMana) {
        this.blackMana = blackMana;
    }

    public void setBlueMana(int blueMana) {
        this.blueMana = blueMana;
    }

    public void setGreenMana(int greenMana) {
        this.greenMana = greenMana;
    }

    public void setRedMana(int redMana) {
        this.redMana = redMana;
    }

    public void setWhiteMana(int whiteMana) {
        this.whiteMana = whiteMana;
    }

    public void setNeutralMana(int neutralMana) {
        this.neutralMana = neutralMana;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
