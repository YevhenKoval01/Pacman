package view.component;

import view.util.FontKit;
import view.util.Palette;

import javax.swing.*;

public class DefaultLabel extends JLabel {

    public final static int DEFAULT_FONT_SIZE = 28;

    public DefaultLabel(String text) {
        this(text, DEFAULT_FONT_SIZE);
    }

    public DefaultLabel(String text, int fontSize) {
        super(text);
        setFont(FontKit.getDefaultFontCustomSize(fontSize));
        setForeground(Palette.MAIN_FG);
    }

}
