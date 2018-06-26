package sk.tomas.snake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Core implements Control, Serializable {

    private int defaultGridHeight = 20;
    private int defaultGridWidth = 10;
    private List<Direction> lastMovements;

    private boolean printMove = true;
    private int score;
    private int appleDistance;
    private boolean appleJustEaten = false;

    private Snake snake;
    private Board board;
    private Node applePosition;

    private Random randomX;
    private Random randomY;

    private void increaseScore() {
        score += 2;
    }

    private void decreaseScore() {
        score -= 3;
    }

    void eatAppleScore() {
        score += 20;
    }

    public Core() {
        lastMovements = new ArrayList<>();
        randomX = new Random();
        randomY = new Random();
        board = new Board(defaultGridWidth, defaultGridHeight);
        snake = new Snake(this, board, board.getAtPosition(defaultGridWidth / 2, defaultGridHeight / 2));
        setAppleAtRandomPosition(true);
        score = 0;
        appleDistance = calculateAppleDistance();
        // board.print();
    }

    @Override
    public boolean move(Direction direction) {
        boolean move = snake.move(direction);

        recalculateScore();

        if (printMove) {
            board.print();
        }
        return move;
    }

    private void recalculateScore() {
        if (appleJustEaten) {
            appleJustEaten = false;
        }
        int temp = calculateAppleDistance();
        if (appleDistance > temp) {
            increaseScore();
        } else {
            if (appleDistance < temp) {
                decreaseScore();
            }
        }
        appleDistance = temp;

    }

    private void printScore() {
        System.out.println("score: " + score);
    }

    void setAppleAtRandomPosition(boolean firstTime) {
        Node randomNode = getRandomNode();
        while (randomNode.isSnakeBody()) {
            randomNode = getRandomNode();
        }
        randomNode.setApple(true);
        applePosition = randomNode;
        if (!firstTime) {
            appleJustEaten = true;
        }
    }

    private int calculateAppleDistance() {
        int x = Math.abs(snake.getHead().getX() - applePosition.getX());
        int y = Math.abs(snake.getHead().getY() - applePosition.getY());
        return x + y;
    }

    public void setPrintMove(boolean printMove) {
        this.printMove = printMove;
    }

    public int getScore() {
        return score;
    }

    @Override
    public Direction calculateBestMove() {
        int temp = Integer.MIN_VALUE;
        Direction result = Direction.UP;
        Core clone1 = (Core) Func.clone(this);
        Core clone2 = (Core) Func.clone(this);
        Core clone3 = (Core) Func.clone(this);
        Core clone4 = (Core) Func.clone(this);

        clone1.setPrintMove(false);
        clone2.setPrintMove(false);
        clone3.setPrintMove(false);
        clone4.setPrintMove(false);

        if (clone1.move(Direction.UP)) {
            if (temp < clone1.getScore()) {
                temp = clone1.getScore();
                result = Direction.UP;
            }
        }
        if (clone2.move(Direction.DOWN)) {
            if (temp < clone2.getScore()) {
                temp = clone2.getScore();
                result = Direction.DOWN;
            }
        }
        if (clone3.move(Direction.LEFT)) {
            if (temp < clone3.getScore()) {
                temp = clone3.getScore();
                result = Direction.LEFT;
            }
        }
        if (clone4.move(Direction.RIGHT)) {
            if (temp < clone4.getScore()) {
                result = Direction.RIGHT;
            }
        }
        return result;
    }

    @Override
    public int wallLeft() {
        return snake.wallLeft();
    }

    @Override
    public int wallRight() {
        return snake.wallRight();
    }

    @Override
    public int wallUp() {
        return snake.wallUp();
    }

    @Override
    public int wallDown() {
        return snake.wallDown();
    }

    @Override
    public int appleLeft() {
        return snake.appleLeft();
    }

    @Override
    public int appleRight() {
        return snake.appleRight();
    }

    @Override
    public int appleUp() {
        return snake.appleUp();
    }

    @Override
    public int appleDown() {
        return snake.appleDown();
    }

    @Override
    public int bodyLeft() {
        return snake.bodyLeft();
    }

    @Override
    public int bodyRight() {
        return snake.bodyRight();
    }

    @Override
    public int bodyUp() {
        return snake.bodyUp();
    }

    @Override
    public int bodyDown() {
        return snake.bodyDown();
    }

    public Node getApplePosition() {
        return applePosition;
    }

    private Node getRandomNode() {
        return board.getAtPosition(randomX.nextInt(defaultGridWidth), randomY.nextInt(defaultGridHeight));
    }
}
