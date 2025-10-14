package model.game;

import controller.util.MapType;
import model.player.GhostColor;
import model.player.GhostModel;
import model.player.PacmanModel;
import model.player.PlayerModel;
import controller.util.BoardBuilder;
import model.util.Position;
import model.util.BoardItem;
import model.util.MoveDirection;

import javax.swing.table.AbstractTableModel;
import java.util.*;

public class GameBoardModel extends AbstractTableModel {

    private final BoardCell[][] board;
    private final PacmanModel pacmanModel;
    private final Map<Integer, GhostModel> ghostModels;

    private GameBoardModel(
            BoardCell[][] board,
            PacmanModel pacmanModel,
            Map<Integer, GhostModel> ghostModels
    ) {
        this.board = board;
        this.pacmanModel = pacmanModel;
        this.ghostModels = ghostModels;
    }

    public static GameBoardModel init(int ghostCount, MapType mapType, int rows, int cols) {
        BoardCell[][] board = null;

        if (mapType != MapType.USER_GENERATED)
            board = BoardBuilder.buildBoard(mapType);
        else {
            board = BoardBuilder.generateBoard(rows, cols);
        }


        Set<Position> generatedPositions = new HashSet<>();
        Position pacPos = BoardBuilder.generateBoardPosition();
        generatedPositions.add(pacPos);

        PacmanModel pacmanModel = new PacmanModel(pacPos);
        board[pacPos.row][pacPos.col].addPlayerToCell(pacmanModel);
        board[pacPos.row][pacPos.col].setItem(BoardItem.SPACE);

        Map<Integer, GhostModel> ghostModels = new HashMap<>();
        GhostColor[] colors = GhostColor.values();

        int colorIdx = 0;
        for (int i = 1; i <= ghostCount; ++i) {
            if (i - 1 >= colors.length) colorIdx = 0;
            Position ghostPos = BoardBuilder.generateBoardPosition();
            while (generatedPositions.contains(ghostPos)) ghostPos = BoardBuilder.generateBoardPosition();
            generatedPositions.add(ghostPos);

            GhostModel gm = new GhostModel(i, colors[colorIdx++], ghostPos);
            board[ghostPos.row][ghostPos.col].addPlayerToCell(gm);
            board[ghostPos.row][ghostPos.col].setItem(BoardItem.SPACE);
            ghostModels.put(i, gm);
        }

        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                BoardCell cell = board[i][j];
                PlayerModel player = cell.getFirstPlayer();

                if (cell.getItem() == BoardItem.SPACE && player == null) {
                    board[i][j].setItem(BoardItem.POINT);
                } else if (player != null && !player.isPacman())
                    board[i][j].setItem(BoardItem.POINT);
            }
        }

        return new GameBoardModel(board, pacmanModel, ghostModels);
    }

    public BoardItem move(MoveDirection direction, PlayerModel playerModel) {
        synchronized (this.board) {
            Position currPos = playerModel.getBoardPosition();
            int nextRow = getNextRow(currPos, direction);
            int nextCol = getNextCol(currPos, direction);

            BoardCell nextCell = this.board[nextRow][nextCol];
            BoardItem nextItem = nextCell.getItem();

            if (nextItem != BoardItem.WALL) {
                this.board[currPos.row][currPos.col].removePlayerFromCell(playerModel);
                this.board[nextRow][nextCol].addPlayerToCell(playerModel);
                playerModel.setBoardPosition(nextRow, nextCol);
                fireTableCellUpdated(currPos.row, currPos.col);
                fireTableCellUpdated(nextRow, nextCol);

                return this.board[nextRow][nextCol].getItem();
            }

            return BoardItem.WALL;
        }
    }

    public void addItem(BoardItem item, Position pos) {
        synchronized (this.board) {
            this.board[pos.row][pos.col].setItem(item);
            fireTableCellUpdated(pos.row, pos.col);
        }
    }

    public void eatItem(Position pos) {
        addItem(BoardItem.SPACE, pos);
    }

    public boolean hasGhosts(Position pos) {
        synchronized (this.board) {
            BoardCell cell = this.board[pos.row][pos.col];
            return cell.currCellPlayerCount() > 1;
        }
    }

    public boolean isSpace(Position pos) {
        return this.board[pos.row][pos.col].getItem() == BoardItem.SPACE;
    }

    public boolean isNextWall(PlayerModel playerModel, MoveDirection direction) {
        Position position = playerModel.getBoardPosition();
        int newRow = getNextRow(position, direction);
        int newCol = getNextCol(position, direction);

        return board[newRow][newCol].getItem() == BoardItem.WALL;
    }

    private int getNextRow(Position pos, MoveDirection direction) {
        int newRow = pos.row + direction.ROW_DIRECTION;
        if (newRow < 0) newRow = this.board.length - 1;
        if (newRow > this.board.length - 1) newRow = 0;
        return newRow;
    }

    private int getNextCol(Position pos, MoveDirection direction) {
        int newCol = pos.col + direction.COL_DIRECTION;
        if (newCol < 0) newCol = this.board[0].length - 1;
        if (newCol > this.board[0].length - 1) newCol = 0;
        return newCol;
    }

    public void setToInitialPositions() {
        this.ghostModels.values().forEach(gm -> {
            Position currPos = gm.getBoardPosition();
            Position initPos = gm.getInitialPosition();
            this.board[currPos.row][currPos.col].removePlayerFromCell(gm);
            this.board[initPos.row][initPos.col].addPlayerToCell(gm);
            gm.setBoardPosition(initPos.row, initPos.col);

            fireTableCellUpdated(currPos.row, currPos.col);
            fireTableCellUpdated(initPos.row, initPos.col);
        });

        Position currPos = this.pacmanModel.getBoardPosition();
        Position initPos = this.pacmanModel.getInitialPosition();
        this.board[currPos.row][currPos.col].removePlayerFromCell(this.pacmanModel);
        this.board[initPos.row][initPos.col].addPlayerToCell(this.pacmanModel);
        this.pacmanModel.setBoardPosition(initPos.row, initPos.col);
        fireTableCellUpdated(currPos.row, currPos.col);
        fireTableCellUpdated(initPos.row, initPos.col);
        this.pacmanModel.changeDirection(null);
    }

    public int getPointsCount() {
        int count = 0;
        for (int i = 0; i < board.length; ++i)
            for (int j = 0; j < board[i].length; ++j)
                if (this.board[i][j].getItem() == BoardItem.POINT)
                    count++;

        return count;
    }

    public PacmanModel getPacmanModel() {
        return this.pacmanModel;
    }

    public GhostModel getGhostModel(int id) {
        return this.ghostModels.getOrDefault(id, null);
    }

    public Map<Integer, GhostModel> getGhostModels() {
        return this.ghostModels;
    }

    @Override
    public int getRowCount() {
        return this.board.length;
    }

    @Override
    public int getColumnCount() {
        return this.board[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.board[rowIndex][columnIndex];
    }

}
