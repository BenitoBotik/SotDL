package com.example.example_project.ui.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private String name;
    private String gm;
    private String map;
    private ArrayList<String> players;
    private String id;
    private ArrayList<Icon> icons;

    // keep this constructor empty for Firebase
    public Game() {
    }

    public Game(String name, String gm, String map, ArrayList<String> players, String id, ArrayList<Icon> icons) {
        this.name = name;
        this.gm = gm;
        this.map = map;
        this.players = players;
        this.id = id;
        this.icons = icons;
    }

    public String getName() {
        return name;
    }

    public String getGm() {
        return gm;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Icon> getIcons() {
        return icons;
    }

    public void setIcons(ArrayList<Icon> icons) {
        this.icons = icons;
    }
}
