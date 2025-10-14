package view.component;

import view.util.FontKit;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormButton extends JButton {

    public FormButton(String buttonText) {
        super(buttonText);

        setFont(FontKit.DEFAULT_FONT);
        setForeground(Palette.MAIN_FG);
        setBackground(Palette.MAIN_BG);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Palette.MAIN_FG, 3));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(300, 60));
        setMaximumSize(new Dimension(400, 80));
        setMinimumSize(new Dimension(200, 50));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Palette.SECONDARY_FG);
                setOpaque(true);
                setBackground(new Color(51, 49, 17));
                setBorder(BorderFactory.createLineBorder(Palette.SECONDARY_FG, 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Palette.MAIN_FG);
                setBackground(Palette.MAIN_BG);
                setBorder(BorderFactory.createLineBorder(Palette.MAIN_FG, 3));
            }
        });
    }

}
