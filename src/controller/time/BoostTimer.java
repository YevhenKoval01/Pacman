package controller.time;

import model.game.GameStateModel;
import view.game.component.BoostBarItem;

public class BoostTimer implements Runnable {

    private final BoostBarItem boostBarItem;
    private final GameStateModel gameState;
    private final boolean updateTimeLabel;
    private int minutes;
    private int secondsRunning;

    public BoostTimer(GameStateModel gameState, int secondsRunning, BoostBarItem item, boolean updateTimeLabel) {
        this.secondsRunning = secondsRunning;
        this.boostBarItem = item;
        this.gameState = gameState;
        this.updateTimeLabel = updateTimeLabel;
    }

    @Override
    public void run() {
        this.minutes = this.secondsRunning / 60;
        this.secondsRunning = this.secondsRunning % 60;

        while (this.minutes >= 0 && this.secondsRunning > 0) {
            if (!this.gameState.isGameRunning()) {break;}
            if (!this.gameState.isGameStopped()) {
                try {
                    if (this.updateTimeLabel) {
                        this.boostBarItem.updateTime(String.format("%02d : %02d", this.minutes, this.secondsRunning));
                    }
                    Thread.sleep(1000);

                    synchronized (this) {
                        if (this.secondsRunning == 0) {
                            if (this.minutes > 0) {
                                this.minutes--;
                                this.secondsRunning = 59;
                            }
                        } else {
                            this.secondsRunning--;
                        }
                    }
                } catch (InterruptedException e) {}
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

    public synchronized void increaseSeconds(int seconds) {
        this.secondsRunning += seconds;
        if (this.secondsRunning >= 60) {
            this.minutes += this.secondsRunning / 60;
            this.secondsRunning = this.secondsRunning % 60;
        }
    }

}
