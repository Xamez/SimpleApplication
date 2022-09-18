package fr.xamez.simpleapplication.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ColoredText {

    private static final List<Object> PARTS = new ArrayList<>();

    public ColoredText() {}

    public ColoredText color(Color color) {
        PARTS.add(color);
        return this;
    }

    public ColoredText style(String text) {
        PARTS.add(text); // TODO ADD STYLE
    }

    public ColoredText text(String text) {
        PARTS.add(text);
    }

    public  toParts() {

    }

}
