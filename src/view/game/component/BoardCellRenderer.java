package view.game.component;

import model.game.BoardCell;
import model.player.GhostModel;
import model.player.PacmanModel;
import model.player.PlayerModel;
import model.util.BoardItem;
import model.util.MoveDirection;
import view.util.Palette;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BoardCellRenderer extends JLabel implements TableCellRenderer {

    public BoardCellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column
    ) {
        setIcon(null);
        BoardCell cell = (BoardCell) value;

        if (cell.currCellPlayerCount() == 0) {
            BoardItem item = cell.getItem();
            switch (item) {
                case SPACE, UNREACHABLE -> {
                    setBackground(Palette.SPACE_COLOR);
                }
                case POINT, SPEED_BOOST, LIFE_BOOST, SHIELD_BOOST, CHERRY_POINT_BOOST, SLOWDOWN_GHOST_BOOST -> {
                    setBackground(Palette.SPACE_COLOR);
                    setIcon(new ImageIcon(item.staticIconPath));
                }
                case WALL -> {
                    setBackground(Palette.WALL_COLOR);
                    setIcon(null);
                }
            }
        } else {
            PlayerModel player = cell.getFirstPlayer();
            setBackground(Palette.SPACE_COLOR);
            if (player != null) {
                if (player.isPacman()) {
                    PacmanModel pm = (PacmanModel) player;

                    MoveDirection currentDirection = pm.getCurrentDirection();
                    String directionString = "right";

                    if (currentDirection != null)
                        directionString = currentDirection.toString().toLowerCase();


                    String iconPath = switch (pm.getMouseState()) {
                        case 0 -> "assets/icons/game/pacman/pacman_openmouse_closed_" + directionString +".png";
                        case 1 -> "assets/icons/game/pacman/pacman_openmouse_small_" + directionString +".png";
                        case 2 -> "assets/icons/game/pacman/pacman_openmouse_mid_" + directionString +".png";
                        case 3 -> "assets/icons/game/pacman/pacman_openmouse_full_" + directionString +".png";
                        default -> null;
                    };
                    if (iconPath != null) setIcon(new ImageIcon(iconPath));
                } else {
                    GhostModel gm = (GhostModel) player;
                    switch (gm.getColor()) {
                        case CYAN -> setIcon(new ImageIcon("assets/icons/game/ghosts/ghost_cyan.png"));
                        case ORANGE -> setIcon(new ImageIcon("assets/icons/game/ghosts/ghost_orange.png"));
                        case RED -> setIcon(new ImageIcon("assets/icons/game/ghosts/ghost_red.png"));
                        case PINK -> setIcon(new ImageIcon("assets/icons/game/ghosts/ghost_pink.png"));
                    }
                }
            }
        }

        return this;
    }
}
