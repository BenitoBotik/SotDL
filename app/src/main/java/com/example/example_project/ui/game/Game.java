package com.example.example_project.ui.game;

public class Game {
    private String name;
    private String gm;
    private String playerOne;
    private String playerTwo;
    private String icon;
    private String playerThree;

    // keep this constructor empty for Firebase
    public Game() {
    }

    public Game(String name, String gm, String playerOne, String playerTwo, String icon, String playerThree) {
        this.name = name;
        this.gm = gm;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.icon = icon;
        this.playerThree = playerThree;
    }

    public String getName() {
        return name;
    }

    public String getGm() {
        return gm;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getIcon() {
        return icon;
    }

    public String getPlayerThree() {
        return playerThree;
    }
}
