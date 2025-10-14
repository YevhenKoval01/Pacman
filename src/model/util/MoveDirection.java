package model.util;

public enum MoveDirection {

    UP(-1,0),

    DOWN(1,0),

    LEFT(0,-1),

    RIGHT(0,1);

    private MoveDirection(int rowDirection, int colDirection) {
        this.ROW_DIRECTION = rowDirection;
        this.COL_DIRECTION = colDirection;
    }

    public final int ROW_DIRECTION;
    public final int COL_DIRECTION;
}
