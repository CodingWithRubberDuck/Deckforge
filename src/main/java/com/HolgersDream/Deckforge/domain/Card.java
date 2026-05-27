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

    private SuperType superType;
    private Type type;
    private Type multiType;
    private String subType;

    private boolean canBeCommander;
    private String picture;
    private String setName;
    private String ruleText;
    private int power;
    private int toughness;
    private Rarity rarity;

    public Card() {
    }

    public Card(int cardId, int blackMana, int blueMana, int greenMana, int redMana, int whiteMana,
                int neutralMana, String name, SuperType superType, Type type, Type multiType,
                String subType, boolean canBeCommander, String picture, String setName, String ruleText,
                int power, int toughness, Rarity rarity) {
        if (cardId < 0){
            throw new IllegalArgumentException("Et kort skal have et gyldigt id");
        }
        this.cardId = cardId;
        if (blackMana < 0){
            throw new IllegalArgumentException("Et korts sorte mana kan ikke være negativ");
        }
        this.blackMana = blackMana;
        if (blueMana < 0){
            throw new IllegalArgumentException("Et korts blå mana kan ikke være negativ");
        }
        this.blueMana = blueMana;
        if (greenMana < 0){
            throw new IllegalArgumentException("Et korts grønne mana kan ikke være negativ");
        }
        this.greenMana = greenMana;
        if (redMana < 0){
            throw new IllegalArgumentException("Et korts røde mana kan ikke være negativ");
        }
        this.redMana = redMana;
        if (whiteMana < 0){
            throw new IllegalArgumentException("Et korts hvide mana kan ikke være negativ");
        }
        this.whiteMana = whiteMana;
        if (neutralMana < 0){
            throw new IllegalArgumentException("Et korts neutrale mana kan ikke være negativ");
        }
        this.neutralMana = neutralMana;

        final int MAX_NAME = 150;
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Et kort skal have et navn");
        } else if (name.length() > MAX_NAME) {
            throw new IllegalArgumentException("Et korts navn kan maksimalt være " + MAX_NAME + " tegn langt");
        }
        this.name = name;

        this.superType = superType;

        if (type == null){
            throw new IllegalArgumentException("Et kort skal have en type");
        }
        this.type = type;

        this.multiType = multiType;

        final int MAX_SUB_TYPE = 150;
        if (subType != null && subType.length() > MAX_SUB_TYPE){
            throw new IllegalArgumentException("Et korts sub-type kan maksimalt være " + MAX_SUB_TYPE + " tegn langt");
        }
        this.subType = subType;

        this.canBeCommander = canBeCommander;

        final int MAX_PICTURE_LENGTH = 300;
        if (picture == null || picture.isBlank()){
            throw new IllegalArgumentException("Et kort skal have et billede");
        } else if (picture.length() > MAX_PICTURE_LENGTH) {
            throw new IllegalArgumentException("Et korts billede kan maksimalt være " + MAX_PICTURE_LENGTH + " tegn langt");
        }
        this.picture = picture;

        final int MAX_SET_NAME = 200;
        if (setName == null || setName.isBlank()){
            throw new IllegalArgumentException("Et kort skal have et sæt-navn");
        } else if (setName.length() > MAX_SET_NAME) {
            throw new IllegalArgumentException("Et korts sæt-navn kan maksimalt være " + MAX_SET_NAME + " tegn langt");
        }
        this.setName = setName;

        final int MAX_RULE_TEXT = 1000;
        if (ruleText != null && ruleText.length() > MAX_RULE_TEXT){
            throw new IllegalArgumentException("En regel-tekst kan maksimalt være " + MAX_RULE_TEXT + " tegn lang");
        }
        this.ruleText = ruleText;

        if (power < 0){
            throw new IllegalArgumentException("Et kort kan ikke have negativ power/styrke");
        }
        this.power = power;
        if (toughness < 0){
            throw new IllegalArgumentException("Et kort kan ikke have negativ toughness/beskyttelse");
        }
        this.toughness = toughness;

        if (rarity == null){
            throw new IllegalArgumentException("Et kort skal have en sjældenhed/rarity");
        }
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

    public SuperType getSuperType() {
        return superType;
    }

    public Type getType() {
        return type;
    }

    public Type getMultiType() {
        return multiType;
    }

    public String getSubType() {
        return subType;
    }

    public boolean isCanBeCommander() {
        return canBeCommander;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
