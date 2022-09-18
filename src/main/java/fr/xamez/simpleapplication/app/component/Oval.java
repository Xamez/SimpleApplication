package fr.xamez.simpleapplication.app.component;

import java.awt.*;

public class Oval extends Component {

    public Oval(int x, int y, double radius, Color color, boolean isFilled) {
        super(x, y, (int) (2 * radius * Math.PI), color, isFilled);
    }

    public Oval(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color, true);
    }

    @Override
    public void render(Graphics graphics) {
        if (isFilled()) {
            graphics.setColor(getColor());
            graphics.fillOval(getX(), getY(), getWidth(), getHeight());
        } else {
            graphics.setColor(getColor());
            graphics.drawOval(getX(), getY(), getWidth(), getHeight());
        }
    }
}
