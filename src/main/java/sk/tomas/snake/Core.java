package sk.tomas.snake;

import java.util.Random;

public class Core implements Control {

    private static final int defaultGridHeight = 10;
    private static final int defaultGridWidth = 7;
    private static final int defaultSnakeLength = 3;

    private Snake snake;
    private Board board;

    private Random randomX;
    private Random randomY;


    Core() {
        randomX = new Random();
        randomY = new Random();
        board = new Board(defaultGridWidth, defaultGridHeight);
        snake = new Snake(this, board, board.getAtPosition(defaultGridWidth / 2, defaultGridHeight / 2));
        setAppleAtRandomPosition();
    }

    @Override
    public boolean move(Direction direction) {
        boolean move = snake.move(direction);
        board.print();
        return move;
    }

    void setAppleAtRandomPosition() {
        Node randomNode = getRandomNode();
        while (randomNode.isSnakeBody()) {
            randomNode = getRandomNode();
        }
        randomNode.setApple(true);
    }

    private Node getRandomNode() {
        return board.getAtPosition(randomX.nextInt(defaultGridWidth), randomY.nextInt(defaultGridHeight));
    }
}
