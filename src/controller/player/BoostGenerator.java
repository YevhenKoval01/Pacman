package controller.player;

import controller.util.RandomUtil;
import model.game.GameBoardModel;
import model.game.GameStateModel;
import model.player.GhostModel;
import model.util.BoardItem;

public class BoostGenerator implements Runnable{

    private final static BoardItem[] boostsToGenerate = {
            BoardItem.SLOWDOWN_GHOST_BOOST,
            BoardItem.LIFE_BOOST,
            BoardItem.SHIELD_BOOST,
            BoardItem.SPEED_BOOST,
            BoardItem.CHERRY_POINT_BOOST
    };

    private static final int GENERATION_DELAY_MILLIS = 5000;
    private final GameBoardModel gameBoard;
    private final GameStateModel gameState;
    private final GhostModel ghostModel;

    public BoostGenerator(
            GameBoardModel model,
            GameStateModel stateModel,
            GhostModel ghostModel
    ) {
        this.gameBoard = model;
        this.gameState = stateModel;
        this.ghostModel = ghostModel;
    }

    @Override
    public void run() {
        while (this.gameState.isGameRunning()) {
            if (!this.gameState.isGameStopped()) {
                try {
                    Thread.sleep(GENERATION_DELAY_MILLIS);
                    BoardItem boardItem = randomizeBoost();
                    if (boardItem != null) {
                        this.ghostModel.setGeneratedBoost(boardItem);
                    }
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

    public static BoardItem randomizeBoost() {
        int n = RandomUtil.randomInt(4);
        if (n == 3) {
            int idx = RandomUtil.randomInt(boostsToGenerate.length);
            return boostsToGenerate[idx];
        }
        return null;
    }
}
