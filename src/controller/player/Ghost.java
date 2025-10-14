package controller.player;

import controller.util.RandomUtil;
import model.game.GameBoardModel;
import model.game.GameStateModel;
import model.player.GhostModel;
import model.util.BoardItem;
import model.util.MoveDirection;

public class Ghost extends Player {

    private int stepsDone;
    private final GhostModel ghostModel;
    private int changeDirectionLimit;

    public Ghost(GameStateModel stateModel, GameBoardModel boardModel, GhostModel model) {
        super(stateModel, boardModel, model);
        this.playerModel.changeDirection(randomizeDirection());
        this.stepsDone = 0;
        this.changeDirectionLimit = RandomUtil.randomInt(2, 9);
        this.ghostModel = model;
    }

    @Override
    protected void onPlayerInit() {
        new Thread(
                new BoostGenerator(this.boardModel, this.gameState, this.ghostModel)
        ).start();
    }

    @Override
    public void onMove(BoardItem boardItem) {
        boolean isNextWall = this.boardModel.isNextWall(playerModel, this.playerModel.getCurrentDirection());

        while (isNextWall) {
            this.playerModel.changeDirection(randomizeDirection());
            isNextWall = this.boardModel.isNextWall(playerModel, this.playerModel.getCurrentDirection());
        }

        this.stepsDone++;

        MoveDirection currentDirection = this.playerModel.getCurrentDirection();
        if (this.stepsDone >= this.changeDirectionLimit) {
            if (currentDirection == MoveDirection.LEFT || currentDirection == MoveDirection.RIGHT) {
                boolean moveUp = canMoveUp();
                boolean moveDown = canMoveDown();

                if (moveUp && moveDown) {
                    boolean up = RandomUtil.randomBool();
                    if (up) this.playerModel.changeDirection(MoveDirection.UP);
                    else this.playerModel.changeDirection(MoveDirection.DOWN);
                }
                else if (moveUp) this.playerModel.changeDirection(MoveDirection.UP);
                else if (moveDown) this.playerModel.changeDirection(MoveDirection.DOWN);

            } else if (currentDirection == MoveDirection.UP || currentDirection == MoveDirection.DOWN) {
                boolean moveLeft = canMoveLeft();
                boolean moveRight = canMoveRight();

                if (moveLeft && moveRight) {
                    boolean left = RandomUtil.randomBool();
                    if (left) this.playerModel.changeDirection(MoveDirection.LEFT);
                    else this.playerModel.changeDirection(MoveDirection.RIGHT);
                }
                else if (moveLeft) this.playerModel.changeDirection(MoveDirection.LEFT);
                else if (moveRight) this.playerModel.changeDirection(MoveDirection.RIGHT);
            }
            this.stepsDone = 0;
            this.changeDirectionLimit = RandomUtil.randomInt(2, 9);
        }

        if (
                this.ghostModel.getLastlyGeneratedBoost() != null &&
                this.boardModel.isSpace(this.ghostModel.getBoardPosition())
        ) {
            this.boardModel.addItem(this.ghostModel.pollLastlyGeneratedBoost(), this.ghostModel.getBoardPosition());
        }
    }

    private boolean canMoveLeft() {
        return !this.boardModel.isNextWall(playerModel, MoveDirection.LEFT);
    }

    private boolean canMoveRight() {
        return !this.boardModel.isNextWall(playerModel, MoveDirection.RIGHT);
    }

    private boolean canMoveUp() {
        return !this.boardModel.isNextWall(playerModel, MoveDirection.UP);
    }

    private boolean canMoveDown() {
        return !this.boardModel.isNextWall(playerModel, MoveDirection.DOWN);
    }

    public MoveDirection randomizeDirection() {
        int n = RandomUtil.randomInt(4);
        if (n == 0) return MoveDirection.UP;
        if (n == 1) return MoveDirection.DOWN;
        if (n == 2) return MoveDirection.LEFT;
        return MoveDirection.RIGHT;
    }
}
