package com.example.example_project.ui.game;

public class Icon {
    private String image;
    private int x;
    private int y;

    // keep this constructor empty for Firebase
    public Icon() {
    }

    public Icon(String image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public String getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void SetX(int x) {
        this.x = x;
    }

    public void SetY(int y) {
        this.y = y;
    }
}
