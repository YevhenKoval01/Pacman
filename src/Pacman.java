import view.menu.MenuFrame;

import javax.swing.*;

public class Pacman {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuFrame menu = MenuFrame.getInstance();
            menu.setVisible(true);
        });
    }

}