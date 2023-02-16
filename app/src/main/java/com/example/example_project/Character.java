package com.example.example_project;

public class Character {

    private String name;
    private String level;
    private String group;
    private String icon;

    public Character(String name, String group, String level, String icon){
        this.name = name;
        this.group = group;
        this.level = level;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getLevel() {
        return level;
    }

    public String getIcon() {
        return icon;
    }
}
