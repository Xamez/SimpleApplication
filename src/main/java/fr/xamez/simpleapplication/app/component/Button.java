package fr.xamez.simpleapplication.app.component;

import java.awt.*;

public class Button extends Rectangle {

    private final Runnable action;

    public Button(int x, int y, int width, int height, Color color, boolean isFilled, Runnable action) {
        super(x, y, width, height, color, isFilled);
        this.action = action;
    }

    public Button(int x, int y, int width, int height, Color color, Runnable action) {
        this(x, y, width, height, color, true, action);
    }

}
