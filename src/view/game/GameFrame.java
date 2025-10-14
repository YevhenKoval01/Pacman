package view.game;

import controller.GameController;
import model.game.GameBoardModel;
import view.component.ApplicationFrame;
import view.game.component.GameBoard;
import view.game.component.GameBoostBar;
import view.game.component.GameStateBar;
import view.util.Palette;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends ApplicationFrame {


    private static GameFrame instance;
    private static GameBoardModel currentBoardModel;

    private GameStateBar statisticBar;
    private GameBoard board;
    private GameBoostBar boostBar;


    private GameFrame() {
        super(
                "Game",
                new Dimension(1000, 800),
                new Dimension(1000, 800)
        );

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @Override
    protected void onComponentsInit() {
        this.statisticBar = new GameStateBar();
        this.board = new GameBoard(currentBoardModel);
        this.boostBar = new GameBoostBar();
        boostBar.setPreferredSize(new Dimension(120, 500));


        JPanel boardWrapper = new JPanel(new GridBagLayout());
        boardWrapper.setBackground(Palette.MAIN_BG);
        boardWrapper.add(board);
        boardWrapper.add(boostBar);

        this.contentPanel.add(this.statisticBar);
        this.contentPanel.add(boardWrapper);
        this.contentPanel.add(Box.createVerticalStrut(50));
    }

    @Override
    protected void onWindowClose() {
        GameController.onGameQuit();
    }

    public GameStateBar getStatesBar() {
        return this.statisticBar;
    }

    public GameBoard getBoard() {
        return this.board;
    }

    public GameBoostBar getBoostBar() {
        return this.boostBar;
    }

    public static GameFrame getInstance(GameBoardModel gameBoardModel) {
        currentBoardModel = gameBoardModel;
        instance = new GameFrame();
        return instance;
    }

}
