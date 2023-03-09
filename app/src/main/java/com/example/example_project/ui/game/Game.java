package com.example.example_project.ui.game;

public class Game {
    private String name;
    private String message;
    private String time;
    private String unread;
    private String icon;
    private String email;

    // keep this constructor empty for Firebase
    public Game() {
    }

    public Game(String name, String message, String time, String unread, String icon, String email) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.unread = unread;
        this.icon = icon;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getUnread() {
        return unread;
    }

    public String getIcon() {
        return icon;
    }

    public String getEmail() {
        return email;
    }
}
