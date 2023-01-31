package com.example.example_project;

public class Chat {
    private String name;
    private String message;
    private String time;
    private String unread;
    private String icon;

    public Chat(String name, String message, String timr, String unread, String icon) {
        this.name = name;
        this.message = message;
        this.time = timr;
        this.unread = unread;
        this.icon = icon;
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
}
