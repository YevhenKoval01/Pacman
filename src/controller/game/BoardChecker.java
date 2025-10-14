package controller.game;

import controller.GameController;
import controller.time.BoostTimer;
import model.game.GameBoardModel;
import model.game.GameStateModel;
import model.player.PacmanModel;
import view.game.component.BoostBarItem;
import view.game.component.GameBoostBar;
import view.game.component.GameStateBar;
import model.util.BoardItem;

import java.util.*;

public class BoardChecker implements Runnable {

    private volatile BoardItem lastEatenItem;
    private final GameStateBar stateBar;
    private final GameStateModel gameState;
    private final GameBoostBar boostBar;
    private final GameBoardModel gameBoard;

    private static final Set<BoardItem> EATABLE_ITEMS = new HashSet<>(
            Arrays.asList(
                    BoardItem.POINT,
                    BoardItem.SPEED_BOOST,
                    BoardItem.CHERRY_POINT_BOOST,
                    BoardItem.SHIELD_BOOST,
                    BoardItem.LIFE_BOOST,
                    BoardItem.SLOWDOWN_GHOST_BOOST
            )
    );

    private final Map<BoardItem, BoostHandler> currentItemHandlerMap;

    public BoardChecker(
            GameStateBar stateBar,
            GameStateModel stateModel,
            GameBoostBar boostBar,
            GameBoardModel gameBoard
    ) {
        this.stateBar = stateBar;
        this.gameState = stateModel;
        this.boostBar = boostBar;
        this.gameBoard = gameBoard;
        this.currentItemHandlerMap = new HashMap<>();

        for (int i = 0; i < this.gameState.getLivesLeft(); ++i)
            this.stateBar.addLife();
    }

    @Override
    public void run() {
        PacmanModel pacmanModel = this.gameBoard.getPacmanModel();

        while (this.gameState.isGameRunning()) {
            if (lastEatenItem != null && !this.gameState.isGameStopped()) {
                if (EATABLE_ITEMS.contains(lastEatenItem)) {
                    this.gameState.increasePoints(lastEatenItem);
                    this.stateBar.updatePoints(this.gameState.getGamePoints());

                    switch (lastEatenItem) {
                        case LIFE_BOOST -> {
                            this.gameState.addLife();
                            this.stateBar.addLife();
                            handleBoost(3, () -> {;}, false);
                        }
                        case CHERRY_POINT_BOOST -> {
                            handleBoost(3, () -> {;}, false);
                        }
                        case SPEED_BOOST -> {
                            if (this.currentItemHandlerMap.get(BoardItem.SPEED_BOOST) == null)
                                pacmanModel.setMovementSpeed(pacmanModel.getMovementSpeed() / 2);

                            handleBoost(15, () -> {
                                pacmanModel.setMovementSpeed(pacmanModel.getMovementSpeed() * 2);
                            }, true);
                        }
                        case SHIELD_BOOST -> {
                            pacmanModel.setVulnerable(false);
                            handleBoost(15, () -> {
                                pacmanModel.setVulnerable(true);
                            }, true);
                        }
                        case SLOWDOWN_GHOST_BOOST -> {
                            if (this.currentItemHandlerMap.get(BoardItem.SLOWDOWN_GHOST_BOOST) == null)
                                this.gameBoard.getGhostModels().values().forEach(m -> m.setMovementSpeed((m.getMovementSpeed() * 2)));

                            handleBoost(15, () -> {
                                BoardChecker.this.gameBoard.getGhostModels().values().forEach(m -> m.setMovementSpeed(m.getMovementSpeed() / 2));
                            }, true);
                        }
                    }

                    this.lastEatenItem = null;
                }

                if (pacmanModel.isVulnerable() && pacmanModel.getCurrentDirection() != null) {
                    if (this.gameBoard.hasGhosts(pacmanModel.getBoardPosition()))
                        ghostEatsPacman();
                }
            } else if (this.gameState.isGameStopped()) {
                synchronized (GameStateModel.gameStoppedLock) {
                    try {
                        while (this.gameState.isGameStopped()) {
                            GameStateModel.gameStoppedLock.wait();
                        }
                    } catch (InterruptedException e) {}
                }
            }
        }

        GameController.onGameFinished();
    }

    public void ghostEatsPacman() {
        this.gameState.stopGame();
        this.gameState.looseLife();
        this.stateBar.removeLife();
        GameController.onLifeLost();

        if (this.gameState.getLivesLeft() == 0) this.gameState.looseGame();
        else this.gameState.continueGame();

    }

    private void handleBoost(int seconds, Runnable callback, boolean updateTimeLabel) {
        if (lastEatenItem != BoardItem.LIFE_BOOST && lastEatenItem != BoardItem.CHERRY_POINT_BOOST) {
            if (this.currentItemHandlerMap.get(this.lastEatenItem) == null) {
                BoostHandler handler = new BoostHandler(this.lastEatenItem, seconds, callback, updateTimeLabel);
                this.currentItemHandlerMap.put(this.lastEatenItem, handler);
                new Thread(handler).start();
            } else {
                BoostHandler currentHandler = this.currentItemHandlerMap.get(this.lastEatenItem);
                currentHandler.increaseBoostTime(seconds);
            }
        } else {
            BoostHandler handler = new BoostHandler(this.lastEatenItem, seconds, callback, updateTimeLabel);
            new Thread(handler).start();
        }
    }

    public void eatItem(BoardItem eatenItem) {
        this.lastEatenItem = eatenItem;
    }

    class BoostHandler implements Runnable {

        private final BoardItem boost;
        private final int seconds;
        private final Runnable callback;
        private final boolean updateTimeLabel;
        private BoostTimer boostTimer;

        public BoostHandler(BoardItem boost, int seconds, Runnable callback, boolean updateTimeLabel) {
            this.boost = boost;
            this.seconds = seconds;
            this.callback = callback;
            this.updateTimeLabel = updateTimeLabel;
        }

        @Override
        public void run() {
            BoostBarItem barItem = new BoostBarItem(boost.staticIconPath);
            this.boostTimer = new BoostTimer(gameState, seconds, barItem, updateTimeLabel);
            Thread timerThread = new Thread(boostTimer);
            BoardChecker.this.boostBar.addItem(barItem);
            timerThread.start();
            try {
                timerThread.join();
                BoardChecker.this.boostBar.remove(barItem);
                this.callback.run();
                BoardChecker.this.currentItemHandlerMap.put(this.boost, null);
                BoardChecker.this.boostBar.repaint();
            } catch (InterruptedException e) {
            }
        }

        public void increaseBoostTime(int seconds) {
            this.boostTimer.increaseSeconds(seconds);
        }
    }


}
