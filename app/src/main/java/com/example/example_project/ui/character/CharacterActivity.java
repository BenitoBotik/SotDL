package com.example.example_project.ui.character;

public class CharacterActivity {

    private String name;
    private String level;
    private String icon;
    private String strength;
    private String agility;
    private String intellect;
    private String will;
    private String id;

    public CharacterActivity(String name, String level, String icon, String strength, String agility, String intellect, String will, String id){
        this.name = name;
        this.level = level;
        this.icon = icon;
        this.strength = strength;
        this.agility = agility;
        this.intellect = intellect;
        this.will = will;
        this.id = id;
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

    public String getId() {
        return id;
    }
}
