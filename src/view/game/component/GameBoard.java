package view.game.component;

import model.game.GameBoardModel;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JTable {

    public GameBoard(GameBoardModel model) {
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setFocusable(false);
        setCellSelectionEnabled(false);
        setTableHeader(null);
        setDefaultRenderer(Object.class, new BoardCellRenderer());
        setModel(model);


        int finalCellSize = calcCellSize(model);

        setRowHeight(finalCellSize);
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setPreferredWidth(finalCellSize);
        }

    }

    private static int calcCellSize(GameBoardModel model) {
        int rowCount = model.getRowCount();
        int finalCellSize;
        if (rowCount <= 35) finalCellSize = 20;
        else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int paddingY = 130;
            int paddingX = 100;
            int availableHeight = screenSize.height - paddingY;
            int availableWidth = screenSize.width - paddingX;
            int cellSizeByHeight = availableHeight / model.getRowCount();
            int cellSizeByWidth = availableWidth / model.getColumnCount();
            finalCellSize = Math.min(cellSizeByHeight, cellSizeByWidth);
        }
        return finalCellSize;
    }

}
