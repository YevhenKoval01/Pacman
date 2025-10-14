package model.player;

import model.util.BoardItem;
import model.util.Position;

import java.util.Objects;

public class GhostModel extends PlayerModel {

    private final int id;
    private GhostColor color;
    private BoardItem lastlyGeneratedBoost;

    public GhostModel(int id, GhostColor color, Position position) {
        super(position, false);
        this.movementSpeed = DEFAULT_GHOST_SPEED;
        this.id = id;
        this.color = color;
        this.lastlyGeneratedBoost = null;
    }

    public int getId() {
        return this.id;
    }

    public GhostColor getColor() {
        return color;
    }

    public void setColor(GhostColor color) {
        this.color = color;
    }

    public BoardItem getLastlyGeneratedBoost() {
        return this.lastlyGeneratedBoost;
    }

    public void setGeneratedBoost(BoardItem boardItem) {
        this.lastlyGeneratedBoost = boardItem;
    }

    public BoardItem pollLastlyGeneratedBoost() {
        BoardItem tmp = this.lastlyGeneratedBoost;
        this.lastlyGeneratedBoost = null;
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GhostModel that = (GhostModel) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "GhostModel( " + "id: " + this.id + ", color: " + color + " )";
    }
}
