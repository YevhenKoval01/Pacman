package controller.player;

import controller.game.BoardChecker;
import model.game.GameStateModel;
import model.player.PacmanModel;
import model.util.BoardItem;
import model.game.GameBoardModel;
import model.util.MoveDirection;

public class Pacman extends Player {

    private final BoardChecker boardChecker;

    public Pacman(
            GameBoardModel boardModel,
            GameStateModel stateModel,
            BoardChecker boardChecker
    ) {
        super(stateModel, boardModel, boardModel.getPacmanModel());
        this.boardChecker = boardChecker;
    }

    public void changeDirection(MoveDirection newDirection) {
        this.playerModel.changeDirection(newDirection);
    }

    @Override
    protected void onPlayerInit() {}

    @Override
    public void onMove(BoardItem eatenItem) {
        PacmanModel model = (PacmanModel) this.playerModel;
        model.updateMouseState();
        this.boardModel.eatItem(this.playerModel.getBoardPosition());
        this.boardChecker.eatItem(eatenItem);
    }
}
