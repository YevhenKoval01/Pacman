package view.scores;

import controller.util.SerializationUtil;
import model.game.GameScore;
import model.game.GameScoreComparator;
import view.component.ApplicationFrame;
import view.component.DefaultLabel;
import view.menu.MenuFrame;
import view.scores.component.GameScoreRenderer;
import view.scores.component.ScoresScrollPane;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresFrame extends ApplicationFrame {

    private static HighScoresFrame instance;

    public HighScoresFrame() {
        super(
                "High scores",
                new Dimension(650, 400),
                new Dimension(800, 800)
        );
    }

    @Override
    protected void onComponentsInit() {
        this.contentPanel.removeAll();

        JLabel title = new DefaultLabel("High scores", 64);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.contentPanel.add(Box.createVerticalStrut(30));
        this.contentPanel.add(title);
        this.contentPanel.add(Box.createVerticalStrut(50));

        List<GameScore> scores = SerializationUtil.readAllScores();
        if (scores.isEmpty()) {
            JLabel noScores = new DefaultLabel("There is no any game yet...");
            noScores.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.contentPanel.add(noScores);
        } else {
            scores.sort(new GameScoreComparator());

            DefaultListModel<GameScore> model = new DefaultListModel<>();
            scores.forEach(model::addElement);

            JList<GameScore> scoresList = new JList<>(model);
            scoresList.setCellRenderer(new GameScoreRenderer());
            scoresList.setBackground(Palette.MAIN_BG);
            ScoresScrollPane scrollPane = new ScoresScrollPane(scoresList);

            this.contentPanel.add(scrollPane);
        }

        revalidate();
    }

    @Override
    protected void onWindowClose() {
        HighScoresFrame.this.dispose();
        MenuFrame.getInstance().setVisible(true);
    }

    public static HighScoresFrame getInstance() {
        if (instance == null) instance = new HighScoresFrame();
        instance.onComponentsInit();
        return instance;
    }

}
