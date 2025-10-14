package controller;

import controller.game.BoardChecker;
import controller.player.Ghost;
import controller.time.GameStopwatch;
import controller.player.Pacman;
import controller.util.MapType;
import controller.util.SerializationUtil;
import model.game.GameBoardModel;
import model.game.GameStateModel;
import view.game.GameFrame;
import model.util.MoveDirection;
import view.menu.MenuFrame;
import model.game.GameScore;
import view.util.DialogUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class GameController {

    private static GameController currentGame;

    private final GameFrame gameFrame;
    private final GameBoardModel gameBoardModel;
    private final GameStateModel gameState;

    private final Pacman pacman;
    private final GameStopwatch timer;
    private final BoardChecker boardChecker;
    private final Map<Integer, Ghost> modelGhostMap;

    private GameController(int ghostCount, MapType mapType, int rows, int cols) {
        this.gameBoardModel = GameBoardModel.init(ghostCount, mapType, rows, cols);
        int generatedPointsCount = this.gameBoardModel.getPointsCount();
        this.gameState = new GameStateModel(generatedPointsCount);
        this.gameFrame = GameFrame.getInstance(this.gameBoardModel);

        this.modelGhostMap = new HashMap<>();


        for (int i = 1; i <= ghostCount; ++i)
            this.modelGhostMap.put(i, new Ghost(this.gameState, this.gameBoardModel, this.gameBoardModel.getGhostModel(i)));

        this.boardChecker = new BoardChecker(
                this.gameFrame.getStatesBar(),
                this.gameState,
                this.gameFrame.getBoostBar(),
                this.gameBoardModel
        );
        this.pacman = new Pacman(this.gameBoardModel, this.gameState, this.boardChecker);
        this.timer = new GameStopwatch(this.gameState, this.gameFrame.getStatesBar());
        setupKeyBindings();
        this.gameFrame.setVisible(true);

        new Thread(this.boardChecker).start();
        new Thread(this.pacman).start();
        new Thread(this.timer).start();
        this.modelGhostMap.values().forEach(g -> new Thread(g).start());
    }

    private void setupKeyBindings() {
        InputMap im = this.gameFrame.getBoard().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.gameFrame.getBoard().getActionMap();

        KeyStroke quitStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);

        im.put(quitStroke, "quit");
        im.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        im.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        im.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        am.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.this.pacman.changeDirection(MoveDirection.UP);
            }
        });
        am.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.this.pacman.changeDirection(MoveDirection.DOWN);
            }
        });
        am.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.this.pacman.changeDirection(MoveDirection.LEFT);
            }
        });
        am.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.this.pacman.changeDirection(MoveDirection.RIGHT);
            }
        });
        am.put("quit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGameQuit();
            }
        });
    }

    public static void onGameQuit() {
        currentGame.gameState.stopGame();

        boolean doQuit = DialogUtil.confirmationDialog(
                currentGame.gameFrame,
                "Quit game",
                "Do you really want to quit the game?"
        );
        if (doQuit == false) {
            currentGame.gameState.continueGame();
        }
        else {
            currentGame.gameState.looseGame();
            currentGame.gameFrame.dispose();
            currentGame = null;
            MenuFrame.getInstance().setVisible(true);
        }
    }

    public static void onLifeLost() {
        DialogUtil.messageDialog(
                currentGame.gameFrame,
                "Oops",
                "Ghosts ate you and you lost your life. Lives left: " + currentGame.gameState.getLivesLeft());
        currentGame.gameBoardModel.setToInitialPositions();
    }

    public static void onGameFinished() {
        if (currentGame.gameState.isGameWin()) {
            String filename = DialogUtil.saveGameDialog(currentGame.gameFrame);
            if (!filename.isEmpty()) {
                GameScore score = currentGame.gameState.buildGameScore(filename);
                boolean isWritten = SerializationUtil.writeScores(score);
                DialogUtil.messageDialog(
                        currentGame.gameFrame,
                        "Game saving",
                        isWritten
                                ? "Your game was successfully written to high score"
                                : "Oops... Something went wrong while saving your game scores..."
                );
            }
        } else if (!currentGame.gameState.isGameWin() && currentGame.gameState.getLivesLeft() == 0){
            DialogUtil.messageDialog(
                    currentGame.gameFrame,
                    "Game lost",
                    "Unfortunately, you lost all your lives... Good luck next time!"
            );
        }
        MenuFrame.getInstance().setVisible(true);
        currentGame.gameFrame.dispose();
        currentGame = null;
    }

    public static void initGame(int ghostCount, MapType mapType, int rows, int cols) {
        currentGame = new GameController(ghostCount, mapType, rows, cols);
    }

}
