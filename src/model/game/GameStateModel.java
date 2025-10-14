package model.game;

import model.util.BoardItem;

public class GameStateModel {

    public final static Object gameStoppedLock = new Object();
    private volatile boolean isGameRunning = true;
    private volatile boolean isGameStopped = false;
    private int minutes = 0;
    private int seconds = 0;
    private int livesLeft = 3;
    private long gamePoints = 0;
    private int eatenPointsCount = 0;
    private final int generatedPointsCount;
    private boolean isGameWin;

    public GameStateModel(int generatedPointsCount) {
        this.generatedPointsCount = generatedPointsCount;
    }

    public String getTimeString() {
        return String.format("%02d", this.minutes) + " : " + String.format("%02d", this.seconds);
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void incMinutes() {
        ++this.minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int incSeconds() {
        return ++this.seconds;
    }

    public int getLivesLeft() {
        return this.livesLeft;
    }

    public void setLivesLeft(int livesLeft) {
        this.livesLeft = livesLeft;
    }

    public long getGamePoints() {
        return this.gamePoints;
    }

    public void increasePoints(BoardItem eatenItem) {
        this.gamePoints += eatenItem.eatValue;
        if (BoardItem.POINT == eatenItem)
            if (++this.eatenPointsCount == this.generatedPointsCount) winGame();
    }

    public boolean isGameWin() {
        return this.isGameWin;
    }

    public void winGame() {
        this.isGameRunning = false;
        this.isGameStopped = true;
        this.isGameWin = true;
    }

    public void looseGame() {
        this.isGameRunning = false;
        this.isGameStopped = true;
        this.isGameWin = false;
    }

    public void stopGame() {
        this.isGameStopped = true;
    }

    public void looseLife() {
        this.livesLeft--;
    }

    public void addLife() {
        this.livesLeft++;
    }

    public void continueGame() {
        this.isGameStopped = false;
        synchronized (gameStoppedLock) {
            gameStoppedLock.notifyAll();
        }
    }

    public boolean isGameRunning() {
        return this.isGameRunning;
    }

    public boolean isGameStopped() {
        return this.isGameStopped;
    }

    public int getEatenPointsCount() {
        return this.eatenPointsCount;
    }

    public int getGeneratedPointsCount() {
        return this.generatedPointsCount;
    }

    public GameScore buildGameScore(String name) {
        return new GameScore(
                this.gamePoints,
                this.seconds,
                this.minutes,
                name
        );
    }

}
