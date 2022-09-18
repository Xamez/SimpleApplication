package fr.xamez.simpleapplication.app.component;

import java.awt.*;

public abstract class Component {

    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private boolean isFilled;

    public Component(int x, int y, int width, int height, Color color, boolean isFilled) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isFilled = isFilled;
    }

    public Component(int x, int y, int width, Color color, boolean isFilled) {
        this(x, y, width, width, color, true);
    }

    public Component(int x, int y, int width, int height, Color color) {
        this(x, y, width, height, color, true);
    }

    public Component(int x, int y, int width, Color color) {
        this(x, y, width, width, color, true);
    }

    public abstract void render(Graphics graphics);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}
