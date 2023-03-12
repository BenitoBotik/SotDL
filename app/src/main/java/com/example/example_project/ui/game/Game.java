package com.example.example_project.ui.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    private String name;
    private String gm;
    private String icon;
    private ArrayList<String> players;
    private String id;

    // keep this constructor empty for Firebase
    public Game() {
    }

    public Game(String name, String gm, String icon, ArrayList<String> players) {
        this.name = name;
        this.gm = gm;
        this.icon = icon;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public String getGm() {
        return gm;
    }

    public String getIcon() {
        return icon;
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
}
