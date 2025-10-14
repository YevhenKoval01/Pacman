package controller.time;

import model.game.GameStateModel;
import view.game.component.GameStateBar;

public class GameStopwatch implements Runnable {

    private final static int SECONDS_LIMIT = 60;
    private final GameStateModel gameState;
    private final GameStateBar stateBar;

    public GameStopwatch(GameStateModel gameState, GameStateBar stateBar) {
        this.gameState = gameState;
        this.stateBar = stateBar;
    }

    @Override
    public void run() {
        while (this.gameState.isGameRunning()) {
            if (!this.gameState.isGameStopped()) {
                try {
                    Thread.sleep(1000);
                    if (this.gameState.incSeconds() >= SECONDS_LIMIT) {
                        this.gameState.incMinutes();
                        this.gameState.setSeconds(0);
                    }
                    this.stateBar.updateTime(this.gameState.getTimeString());
                } catch (InterruptedException e) {
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
    }

}
