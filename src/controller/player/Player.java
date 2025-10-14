package controller.player;

import model.game.GameBoardModel;
import model.game.GameStateModel;
import model.player.PlayerModel;
import model.util.BoardItem;

public abstract class Player implements Movable, Runnable {

    protected GameStateModel gameState;
    protected GameBoardModel boardModel;
    protected PlayerModel playerModel;

    protected Player(
            GameStateModel gameStateModel,
            GameBoardModel gameBoardModel,
            PlayerModel playerModel
    ) {
        this.gameState = gameStateModel;
        this.boardModel = gameBoardModel;
        this.playerModel = playerModel;
    }


    public abstract void onMove(BoardItem boardItem);
    protected abstract void onPlayerInit();

    @Override
    public void run() {
        onPlayerInit();
        while (this.gameState.isGameRunning()) {
            if (!this.gameState.isGameStopped()) {
                if (this.playerModel.getCurrentDirection() != null) {
                    try {
                        Thread.sleep(this.playerModel.getMovementSpeed());
                        BoardItem boardItem = this.boardModel.move(this.playerModel.getCurrentDirection(), this.playerModel);
                        onMove(boardItem);
                    } catch (InterruptedException e) {
                    }
                }
            } else {
                synchronized (GameStateModel.gameStoppedLock) {
                    try {
                        while (this.gameState.isGameStopped()) {
                            GameStateModel.gameStoppedLock.wait();
                        }
                    } catch (InterruptedException e) {}
                }
            }
        }
        this.playerModel.changeDirection(null);
    }
}
