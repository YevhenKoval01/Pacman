package view.util;

import view.component.DefaultLabel;

import java.awt.*;

public class FontKit {

    public static Font DEFAULT_FONT = new Font("Arial Black", Font.BOLD, DefaultLabel.DEFAULT_FONT_SIZE);

    public static Font getDefaultFontCustomSize(int size) {
        return new Font("Arial Black", Font.BOLD, size);
    }

}
