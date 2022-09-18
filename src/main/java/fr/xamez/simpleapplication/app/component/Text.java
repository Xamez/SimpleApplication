package fr.xamez.simpleapplication.app.component;

import fr.xamez.simpleapplication.utils.ColoredText;
import net.kyori.adventure.text.TextComponent;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class Text extends Component {

    private final ColoredText text;
    private final Font font;

    public Text(int x, int y, Font font, ColoredText text) {
        super(x, y, getTextWidth(font, text.toString()), getTextHeight(font, text.toString()), null, true);
        this.font = font;
        this.text = text;
    }

    public Text(int x, int y, ColoredText text) {
        this(x, y, new Font(null, Font.PLAIN, 12), text);
    }

    private static final int getTextWidth(Font font, String text) {
        return (int) font.getStringBounds(text, new FontRenderContext(font.getTransform(), true, true)).getWidth();
    }

    private static final int getTextHeight(Font font, String text) {
        return (int) font.getStringBounds(text, new FontRenderContext(font.getTransform(), true, true)).getHeight();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(getColor());
        //graphics.drawString(textComponent.content(), getX(), getY());
    }

    public ColoredText getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }
}
