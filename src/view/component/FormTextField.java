package view.component;

import view.util.FontKit;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FormTextField extends JTextField {

    public FormTextField(int columns) {
        super(columns);

        setFont(FontKit.DEFAULT_FONT);
        setForeground(Palette.MAIN_FG);
        setBackground(Palette.MAIN_BG);
        setCaretColor(Palette.MAIN_FG);
        setBorder(BorderFactory.createLineBorder(Palette.MAIN_FG, 3));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(400, 60));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setForeground(Palette.SECONDARY_FG);
                setBackground(new Color(51, 49, 17));
                setBorder(BorderFactory.createLineBorder(Palette.SECONDARY_FG, 3));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setForeground(Palette.MAIN_FG);
                setBackground(Palette.MAIN_BG);
                setBorder(BorderFactory.createLineBorder(Palette.MAIN_FG, 3));
            }
        });
    }

}
