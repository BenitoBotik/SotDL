package com.example.example_project.ui.game;

public class Icon {
    private int image;
    private float x;
    private float y;

    public Icon(int image, float x, float y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public int getImage() {
        return image;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
