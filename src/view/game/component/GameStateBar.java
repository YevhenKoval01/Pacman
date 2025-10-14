package view.game.component;

import view.component.DefaultLabel;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;

public class GameStateBar extends JPanel {

    private final JLabel timeLabel;
    private final JLabel pointsLabel;
    private final JPanel livesPanel;

    public GameStateBar() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        setBackground(Palette.MAIN_BG);

        this.timeLabel = new DefaultLabel("Time 00 : 00");
        this.pointsLabel = new DefaultLabel("Points 0");

        this.livesPanel = new JPanel();
        this.livesPanel.setLayout(new FlowLayout());
        this.livesPanel.setBackground(Palette.MAIN_BG);

        add(this.timeLabel);
        add(Box.createHorizontalGlue());
        add(this.livesPanel);
        add(Box.createHorizontalGlue());
        add(this.pointsLabel);
    }

    public void addLife() {
        ImageIcon heartIcon = new ImageIcon("assets/icons/game/boosts/life_boost.png");
        livesPanel.add(new JLabel(heartIcon));
    }

    public void removeLife() {
        this.livesPanel.remove(this.livesPanel.getComponents().length - 1);
    }

    public void updateTime(String time) {
        this.timeLabel.setText("Time " + time);
    }

    public void updatePoints(long currentPoints) {
        this.pointsLabel.setText("Points " + currentPoints);
    }

}
