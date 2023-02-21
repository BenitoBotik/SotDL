package com.example.example_project;

public class Character {

    private String name;
    private String level;
    private String icon;
    private String strength;
    private String agility;
    private String intellect;
    private String will;

    public Character(String name, String level, String icon, String strength, String agility, String intellect, String will){
        this.name = name;
        this.level = level;
        this.icon = icon;
        this.strength = strength;
        this.agility = agility;
        this.intellect = intellect;
        this.will = will;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getIcon() {
        return icon;
    }

    public String getStrength() {
        return strength;
    }

    public String getAgility() {
        return agility;
    }

    public String getIntellect() {
        return intellect;
    }

    public String getWill() {
        return will;
    }
}
