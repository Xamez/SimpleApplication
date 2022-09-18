package fr.xamez.simpleapplication.app.component;

import java.awt.*;

public class Rectangle extends Component {

    public Rectangle(int x, int y, int width, int height, Color color, boolean isFilled) {
        super(x, y, width, height, color, isFilled);
    }

    public Rectangle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color, true);
    }

    @Override
    public void render(Graphics graphics) {
        if (isFilled()) {
            graphics.setColor(getColor());
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        } else {
            graphics.setColor(getColor());
            graphics.drawRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
