package model.player;

import model.util.Position;

public class PacmanModel extends PlayerModel{

    private boolean isVulnerable;
    private int mouseState;

    public PacmanModel(Position position) {
        super(position, true);
        this.isVulnerable = true;
        this.movementSpeed = PlayerModel.DEFAULT_PACMAN_SPEED;
        this.mouseState = 0;
    }

    public boolean isVulnerable() {
        return this.isVulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }

    public int getMouseState() {
        return this.mouseState;
    }

    public void updateMouseState() {
        if (++this.mouseState > 3)
            this.mouseState = 0;
    }

}
