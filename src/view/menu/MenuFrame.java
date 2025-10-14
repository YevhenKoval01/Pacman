package view.menu;

import view.component.ApplicationFrame;
import view.component.DefaultLabel;
import view.game.BoardCreationFrame;
import view.component.FormButton;
import view.scores.HighScoresFrame;
import view.util.DialogUtil;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends ApplicationFrame {

    private static MenuFrame instance;

    private MenuFrame() {
        super(
                "PACMAN",
                new Dimension(450, 450),
                new Dimension(1000, 600)
        );
    }

    @Override
    protected void onComponentsInit() {
        JLabel titleLabel = new DefaultLabel("PACMAN", 72);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.contentPanel.add(Box.createVerticalGlue());
        this.contentPanel.add(titleLabel);
        this.contentPanel.add(Box.createVerticalStrut(40));

        String[] optionLabels = {"NEW GAME", "HIGH SCORES", "EXIT"};
        FormButton[] menuButtons = new FormButton[3];

        for (int i = 0; i < optionLabels.length; ++i) {
            FormButton button = new FormButton(optionLabels[i]);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButtons[i] = button;
            this.contentPanel.add(button);
            this.contentPanel.add(Box.createVerticalStrut(20));
        }

        FormButton newGame = menuButtons[0];
        FormButton highScores = menuButtons[1];
        FormButton exit = menuButtons[2];

        newGame.addActionListener(e -> {
            MenuFrame.this.dispose();
            BoardCreationFrame.getInstance().setVisible(true);
        });

        highScores.addActionListener(e -> {
            MenuFrame.this.dispose();
            HighScoresFrame.getInstance().setVisible(true);
        });

        exit.addActionListener(e -> onWindowClose());

        this.contentPanel.add(Box.createVerticalGlue());
    }

    @Override
    protected void onWindowClose() {
        boolean choice = DialogUtil.confirmationDialog(this, "Exit", "Do you really want to quit the application?");
        if (choice) this.dispose();
    }

    public static MenuFrame getInstance() {
        if (instance == null) instance = new MenuFrame();
        return instance;
    }


}
