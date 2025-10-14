package view.game.component;

import view.util.Palette;

import javax.swing.*;

public class GameBoostBar extends JPanel {

    public GameBoostBar() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Palette.MAIN_BG);
    }

    public void addItem(BoostBarItem item) {
        add(item);
    }

}
