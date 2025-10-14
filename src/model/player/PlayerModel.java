package model.player;

import model.util.MoveDirection;
import model.util.Position;

import java.util.Objects;
import java.util.UUID;

public abstract class PlayerModel {

    public static int DEFAULT_PACMAN_SPEED = 220;
    public static int DEFAULT_GHOST_SPEED = 205;

    protected final String PLAYER_ID;
    protected volatile MoveDirection currentDirection;
    protected final Position initialPosition;
    protected int movementSpeed;
    protected Position boardPosition;
    protected boolean isPacman;

    protected PlayerModel(Position initialPosition, boolean isPacman) {
        this.PLAYER_ID = UUID.randomUUID().toString();
        this.initialPosition = initialPosition;
        this.boardPosition = initialPosition;
        this.currentDirection = null;
        this.isPacman = isPacman;
    }

    public Position getInitialPosition() {
        return this.initialPosition;
    }

    public int getMovementSpeed() {
        return this.movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public Position getBoardPosition() {
        return this.boardPosition;
    }

    public void setBoardPosition(Position boardPosition) {
        this.boardPosition = boardPosition;
    }

    public void setBoardPosition(int row, int col) {
        this.boardPosition = new Position(row, col);
    }

    public MoveDirection getCurrentDirection() {
        return this.currentDirection;
    }

    public void changeDirection(MoveDirection newDirection) {
        this.currentDirection = newDirection;
    }

    public boolean isPacman() {
        return this.isPacman;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerModel that = (PlayerModel) o;
        return Objects.equals(this.PLAYER_ID, that.PLAYER_ID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.PLAYER_ID);
    }
}
