package view.scores.component;

import model.game.GameScore;
import view.component.DefaultLabel;
import view.util.FontKit;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class GameScoreRenderer extends JPanel implements ListCellRenderer<GameScore> {

    private final JLabel gameNameLabel;
    private final JLabel gameInfoLabel;

    public GameScoreRenderer() {
        super();
        this.gameNameLabel = new DefaultLabel("", 20);
        this.gameInfoLabel = new DefaultLabel("", 16);
        this.gameNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.gameInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setBackground(Palette.MAIN_BG);

        add(this.gameNameLabel, BorderLayout.NORTH);
        add(this.gameInfoLabel, BorderLayout.SOUTH);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends GameScore> list, GameScore value, int index, boolean isSelected, boolean cellHasFocus) {
        this.gameNameLabel.setText(value.getGameName() + " - " + value.getGainedPoints() + " points");

        String time = String.format("%02d:%02d", value.getMinutes(), value.getSeconds());
        String date = value.getPlayedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.gameInfoLabel.setText("Time: " + time + "   Played date: " + date);

        setBackground(isSelected ? Palette.WALL_COLOR : Palette.MAIN_BG);

        return this;
    }
}
