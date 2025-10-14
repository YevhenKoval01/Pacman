package controller.util;

import model.game.BoardCell;
import model.util.MoveDirection;
import model.util.Position;
import model.util.BoardItem;

import java.util.*;

public class BoardBuilder {

    private static BoardItem[][] generatedBoard;

    public static BoardCell[][] buildBoard(MapType mapType) {
        generatedBoard = SerializationUtil.readMap(mapType);

        if (generatedBoard != null) {
            return getBoardCells();
        } else return generateBoard(30, 30);
    }

    private static MoveDirection generateDirection() {
        int n = RandomUtil.randomInt(4);
        if (n == 0) return MoveDirection.LEFT;
        else if (n == 1) return MoveDirection.RIGHT;
        else if (n == 2) return MoveDirection.UP;
        else return MoveDirection.DOWN;
    }

    private static BoardCell[][] getBoardCells() {
        BoardCell[][] board = new BoardCell[generatedBoard.length][generatedBoard[0].length];

        for (int i = 0; i < generatedBoard.length; ++i)
            for (int j = 0; j < generatedBoard[i].length; ++j)
                board[i][j] = new BoardCell(generatedBoard[i][j]);

        return board;
    }

    public static Position generateBoardPosition() {
        boolean isPositionSet = false;
        int rows = generatedBoard.length;
        int cols = generatedBoard[0].length;
        Position position = null;

        while (!isPositionSet) {
            int row = RandomUtil.randomInt(rows);
            int col = RandomUtil.randomInt(cols);

            if (generatedBoard[row][col] == BoardItem.SPACE || generatedBoard[row][col] == BoardItem.POINT) {
                position = new Position(row, col);
                isPositionSet = true;
            }
        }

        return position;
    }


    public static BoardCell[][] generateBoard(int rowsCount, int colsCount) {
        generatedBoard = new BoardItem[rowsCount][colsCount];
        for (BoardItem[] boardItems : generatedBoard) Arrays.fill(boardItems, BoardItem.WALL);
        mazeMiner(3, (generatedBoard.length * generatedBoard[0].length) / 5);
        mazeMiner(5, (generatedBoard.length * generatedBoard[0].length) / 2);
        return getBoardCells();
    }

    private static void dig(int row, int col, BoardItem item) {
        generatedBoard[row][col] = item;
        generatedBoard[row][generatedBoard[row].length - 1 - col] = item;
    }

    private static void mazeMiner(int dirChangeLimit, int stepCountLimit) {
        MoveDirection currentDirection = generateDirection();

        int stepsDone = 0;
        int currRow = RandomUtil.randomInt(0, generatedBoard.length - 1);
        int currCol = RandomUtil.randomInt(0, generatedBoard[0].length - 1);

        while (stepsDone <= stepCountLimit) {
            dig(currRow, currCol, BoardItem.POINT);
            if (stepsDone % dirChangeLimit == 0) {
                MoveDirection prevDirection = currentDirection;
                while (prevDirection == currentDirection)
                    currentDirection = generateDirection();
            }

            currRow = getNextRow(new Position(currRow, currCol), currentDirection);
            currCol = getNextCol(new Position(currRow, currCol), currentDirection);

            stepsDone++;
        }
    }

    private static int getNextRow(Position pos, MoveDirection direction) {
        int newRow = pos.row + direction.ROW_DIRECTION;
        if (newRow < 0) newRow = generatedBoard.length - 1;
        if (newRow > generatedBoard.length - 1) newRow = 0;
        return newRow;
    }

    private static int getNextCol(Position pos, MoveDirection direction) {
        int newCol = pos.col + direction.COL_DIRECTION;
        if (newCol < 0) newCol = generatedBoard[0].length - 1;
        if (newCol > generatedBoard[0].length - 1) newCol = 0;
        return newCol;
    }

    private static void connect(Position source, Position destination) {

        if (source.row == destination.row) {
            drawHorizontalLine(
                    Math.min(source.col, destination.col),
                    Math.max(source.col, destination.col),
                    source.row,
                    BoardItem.POINT
            );
        } else if (source.col == destination.col) {
            drawVerticalLine(
                    Math.min(source.row, destination.row),
                    Math.max(source.row, destination.row),
                    source.col,
                    BoardItem.POINT
            );
        } else {
            drawHorizontalLine(
                    Math.min(source.col, destination.col),
                    Math.max(source.col, destination.col),
                    source.row,
                    BoardItem.POINT
            );
            drawVerticalLine(
                    Math.min(source.row, destination.row),
                    Math.max(source.row, destination.row),
                    source.col,
                    BoardItem.POINT
            );
        }
    }

    private static void drawVerticalLine(int startRowIdx, int endRowIdx, int colIdx, BoardItem item) {
        int symmetricColIdx = generatedBoard[0].length - 1 - colIdx;
        for (; startRowIdx <= endRowIdx; ++startRowIdx) {
            generatedBoard[startRowIdx][colIdx] = item;
            generatedBoard[startRowIdx][symmetricColIdx] = item;
        }
    }

    private static void drawHorizontalLine(int startColIdx, int endColIdx, int rowIdx, BoardItem item) {
        int symmetricStartColIdx = generatedBoard[0].length - 1 - startColIdx;

        for (; startColIdx <= endColIdx; ++startColIdx) {
            generatedBoard[rowIdx][startColIdx] = item;
            generatedBoard[rowIdx][symmetricStartColIdx--] = item;
        }
    }

}