package view.component;

import javax.swing.*;
import java.awt.*;

public class Image extends JLabel {


    public Image(String path) {
        setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH)));
        setPreferredSize(new Dimension(400, 400));
        setOpaque(false);
    }

}
