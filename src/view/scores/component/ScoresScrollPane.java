package view.scores.component;

import view.util.Palette;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ScoresScrollPane extends JScrollPane {

    public ScoresScrollPane(Component component) {
        super(component);

        setBackground(Palette.MAIN_BG);
        getViewport().setBackground(Palette.MAIN_BG);
        setBorder(BorderFactory.createEmptyBorder());
        getVerticalScrollBar().setBackground(Palette.MAIN_BG);
        getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Palette.MAIN_FG;
                this.trackColor = Palette.MAIN_BG;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createEmptyBtn();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createEmptyBtn();
            }

            private JButton createEmptyBtn() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0, 0));
                btn.setMinimumSize(new Dimension(0, 0));
                btn.setMaximumSize(new Dimension(0, 0));
                return btn;
            }
        });
    }

}
