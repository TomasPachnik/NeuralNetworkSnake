package sk.tomas.snake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeImpl implements Snake, Serializable {

    private int defaultGridHeight = 20;
    private int defaultGridWidth = 10;
    private List<Direction> lastMovements;

    private boolean printMove = true;
    private int score;
    private int appleDistance;
    private boolean appleJustEaten = false;

    private Body body;
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

    public SnakeImpl() {
        lastMovements = new ArrayList<>();
        randomX = new Random();
        randomY = new Random();
        board = new Board(defaultGridWidth, defaultGridHeight);
        body = new Body(this, board, board.getAtPosition(defaultGridWidth / 2, defaultGridHeight / 2));
        setAppleAtRandomPosition(true);
        score = 0;
        appleDistance = calculateAppleDistance();
        // board.print();
    }

    @Override
    public boolean move(Direction direction) {
        boolean move = body.move(direction);
        recalculateScore();
        if (printMove) {
            board.print();
        }
        return move;
    }

    @Override
    public boolean move(double[] array) {
        return move(array[0], array[1], array[2]);
    }

    @Override
    public boolean move(double left, double forward, double right) {
        double result = Math.max(Math.max(left, forward), right);
        Direction neck = getNeckPosition();
        if (result == left) {
            switch (neck) {
                case LEFT:
                    return move(Direction.UP);
                case DOWN:
                    return move(Direction.LEFT);
                case RIGHT:
                    return move(Direction.DOWN);
                case UP:
                    return move(Direction.RIGHT);
            }
        } else if (result == forward) {
            switch (neck) {
                case LEFT:
                    return move(Direction.RIGHT);
                case DOWN:
                    return move(Direction.UP);
                case RIGHT:
                    return move(Direction.LEFT);
                case UP:
                    return move(Direction.DOWN);
            }
        } else if (result == right) {
            switch (neck) {
                case LEFT:
                    return move(Direction.DOWN);
                case DOWN:
                    return move(Direction.RIGHT);
                case RIGHT:
                    return move(Direction.UP);
                case UP:
                    return move(Direction.LEFT);
            }
        }
        return false;
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
        int x = Math.abs(body.getHead().getX() - applePosition.getX());
        int y = Math.abs(body.getHead().getY() - applePosition.getY());
        return x + y;
    }

    public void setPrintMove(boolean printMove) {
        this.printMove = printMove;
    }

    public int getScore() {
        return score;
    }

    @Override
    public double[] directionToArray(Direction direction) {
        Direction neck = getNeckPosition();
        double[] result = new double[3];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }
        if (direction == Direction.LEFT) {
            switch (neck) {
                case DOWN:
                    result[0] = 1;
                    break;
                case RIGHT:
                    result[1] = 1;
                    break;
                case UP:
                    result[2] = 1;
                    break;
            }
        } else {
            if (direction == Direction.UP) {
                switch (neck) {
                    case DOWN:
                        result[1] = 1;
                        break;
                    case RIGHT:
                        result[2] = 1;
                        break;
                    case LEFT:
                        result[0] = 1;
                        break;
                }
            } else {
                if (direction == Direction.DOWN) {
                    switch (neck) {
                        case LEFT:
                            result[2] = 1;
                            break;
                        case RIGHT:
                            result[0] = 1;
                            break;
                        case UP:
                            result[1] = 1;
                            break;
                    }
                } else {
                    if (direction == Direction.RIGHT) {
                        switch (neck) {
                            case DOWN:
                                result[2] = 1;
                                break;
                            case LEFT:
                                result[1] = 1;
                                break;
                            case UP:
                                result[0] = 1;
                                break;
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Direction calculateBestMove() {
        int temp = Integer.MIN_VALUE;
        Direction result = Direction.UP;
        SnakeImpl clone1 = (SnakeImpl) Func.clone(this);
        SnakeImpl clone2 = (SnakeImpl) Func.clone(this);
        SnakeImpl clone3 = (SnakeImpl) Func.clone(this);
        SnakeImpl clone4 = (SnakeImpl) Func.clone(this);

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
        return body.wallLeft();
    }

    @Override
    public int wallRight() {
        return body.wallRight();
    }

    @Override
    public int wallUp() {
        return body.wallUp();
    }

    @Override
    public int wallDown() {
        return body.wallDown();
    }

    @Override
    public int appleLeft() {
        return body.appleLeft();
    }

    @Override
    public int appleRight() {
        return body.appleRight();
    }

    @Override
    public int appleUp() {
        return body.appleUp();
    }

    @Override
    public int appleDown() {
        return body.appleDown();
    }

    @Override
    public int bodyLeft() {
        return body.bodyLeft();
    }

    @Override
    public int bodyRight() {
        return body.bodyRight();
    }

    @Override
    public int bodyUp() {
        return body.bodyUp();
    }

    @Override
    public int bodyDown() {
        return body.bodyDown();
    }

    @Override
    public double[] output() {
        double[] result = new double[9];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }

        Direction back = getNeckPosition();
        switch (back) {
            case LEFT:
                if (appleUp() > 0) {
                    result[0] = 1;
                }
                if (appleRight() > 0) {
                    result[1] = 1;
                }
                if (appleDown() > 0) {
                    result[2] = 1;
                }
                if (wallUp() > 0) {
                    result[3] = 1;
                }
                if (wallRight() > 0) {
                    result[4] = 1;
                }
                if (wallDown() > 0) {
                    result[5] = 1;
                }
                if (bodyUp() > 0) {
                    result[6] = 1;
                }
                if (bodyRight() > 0) {
                    result[7] = 1;
                }
                if (bodyDown() > 0) {
                    result[8] = 1;
                }
                break;
            case DOWN:
                if (appleLeft() > 0) {
                    result[0] = 1;
                }
                if (appleUp() > 0) {
                    result[1] = 1;
                }
                if (appleRight() > 0) {
                    result[2] = 1;
                }
                if (wallLeft() > 0) {
                    result[3] = 1;
                }
                if (wallUp() > 0) {
                    result[4] = 1;
                }
                if (wallRight() > 0) {
                    result[5] = 1;
                }
                if (bodyLeft() > 0) {
                    result[6] = 1;
                }
                if (bodyUp() > 0) {
                    result[7] = 1;
                }
                if (bodyRight() > 0) {
                    result[8] = 1;
                }
                break;
            case RIGHT:
                if (appleDown() > 0) {
                    result[0] = 1;
                }
                if (appleLeft() > 0) {
                    result[1] = 1;
                }
                if (appleUp() > 0) {
                    result[2] = 1;
                }
                if (wallDown() > 0) {
                    result[3] = 1;
                }
                if (wallLeft() > 0) {
                    result[4] = 1;
                }
                if (wallUp() > 0) {
                    result[5] = 1;
                }
                if (bodyDown() > 0) {
                    result[6] = 1;
                }
                if (bodyLeft() > 0) {
                    result[7] = 1;
                }
                if (bodyUp() > 0) {
                    result[8] = 1;
                }
                break;
            case UP:
                if (appleRight() > 0) {
                    result[0] = 1;
                }
                if (appleDown() > 0) {
                    result[1] = 1;
                }
                if (appleUp() > 0) {
                    result[2] = 1;
                }
                if (wallRight() > 0) {
                    result[3] = 1;
                }
                if (wallDown() > 0) {
                    result[4] = 1;
                }
                if (wallLeft() > 0) {
                    result[5] = 1;
                }
                if (bodyRight() > 0) {
                    result[6] = 1;
                }
                if (bodyDown() > 0) {
                    result[7] = 1;
                }
                if (bodyLeft() > 0) {
                    result[8] = 1;
                }
                break;
        }
        return result;
    }


    private Direction getNeckPosition() {
        if (body.getBody().get(0).getX() > body.getBody().get(1).getX()) {
            return Direction.UP;
        } else if (body.getBody().get(0).getX() < body.getBody().get(1).getX()) {
            return Direction.DOWN;
        }
        if (body.getBody().get(0).getY() > body.getBody().get(1).getY()) {
            return Direction.LEFT;
        } else if (body.getBody().get(0).getY() < body.getBody().get(1).getY()) {
            return Direction.RIGHT;
        }
        return Direction.UP;
    }

    public Node getApplePosition() {
        return applePosition;
    }

    private Node getRandomNode() {
        return board.getAtPosition(randomX.nextInt(defaultGridWidth), randomY.nextInt(defaultGridHeight));
    }
}
