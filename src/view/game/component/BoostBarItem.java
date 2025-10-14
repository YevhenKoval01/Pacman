package view.game.component;

import view.component.DefaultLabel;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;

public class BoostBarItem extends JPanel {

    private final JLabel timeLabel;

    public BoostBarItem(String iconPath) {
        super();

        setLayout(new FlowLayout());
        setBackground(Palette.MAIN_BG);

        JLabel boostLabel = new DefaultLabel("");
        boostLabel.setIcon(new ImageIcon(iconPath));
        boostLabel.setIconTextGap(8);
        boostLabel.setHorizontalAlignment(SwingConstants.LEFT);
        boostLabel.setHorizontalTextPosition(SwingConstants.LEFT);

        this.timeLabel = new DefaultLabel("");

        add(boostLabel);
        add(timeLabel);
    }

    public void updateTime(String text) {
        this.timeLabel.setText(text);
    }

}
