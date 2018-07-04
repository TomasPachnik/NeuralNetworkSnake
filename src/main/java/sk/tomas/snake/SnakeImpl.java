package sk.tomas.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeImpl implements Snake {

    private List<Node> body;
    private final int width; //sirka
    private final int height; //vyska
    private Rotation lastRotation;
    private Node apple;
    private Random randomX;
    private Random randomY;
    private int score; //normal score
    private int lastHelpScore;
    private int helpScore; //score for neural network

    public SnakeImpl(int width, int height) {
        this.width = width;
        this.height = height;
        randomX = new Random();
        randomY = new Random();
        this.body = generateBody();
        this.lastRotation = calculateLastRotation();
        this.apple = generateApple();
        lastHelpScore = calculateHelpScore();
    }

    private Node generateApple() {
        Node apple = new Node(randomX.nextInt(height), randomY.nextInt(width));
        while (isBody(apple.getX(), apple.getY())) {
            apple = new Node(randomX.nextInt(height), randomY.nextInt(width));
        }
        return apple;
    }

    private Rotation calculateLastRotation() {
        Rotation rotation = null;
        if (body.get(0).getX() > body.get(1).getX()) {
            rotation = Rotation.DOWN;
        } else if (body.get(0).getX() < body.get(1).getX()) {
            rotation = Rotation.UP;
        } else if (body.get(0).getY() < body.get(1).getY()) {
            rotation = Rotation.LEFT;
        } else if (body.get(0).getY() > body.get(1).getY()) {
            rotation = Rotation.RIGHT;
        }
        return rotation;
    }

    @Override
    public boolean move(Movement movement) {
        switch (movement) {
            case FORWARD:
                return move(lastRotation);
            case LEFT:
                return move(Rotation.left(lastRotation));
            case RIGHT:
                return move(Rotation.right(lastRotation));
        }
        return false;
    }

    @Override
    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (isBody(i, j)) {
                    if ((i == body.get(0).getX()) && (j == body.get(0).getY())) {
                        System.out.print("X");
                    } else {
                        System.out.print("x");
                    }
                } else {
                    if ((i == apple.getX()) && (j == apple.getY())) {
                        System.out.print("o");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public int score() {
        return score;
    }

    @Override
    public int fitness() {
        return helpScore;
    }

    @Override
    public double[] actualInfo() {
        double[] result = new double[(width * height) + 20];
        String s1 = Integer.toBinaryString(apple.getX());
        String s2 = Integer.toBinaryString(apple.getY());
        String s3 = Integer.toBinaryString(body.get(0).getX());
        String s4 = Integer.toBinaryString(body.get(0).getY());

        int index = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (isBody(i, j)) {
                    result[index] = 1;
                }
                index++;
            }
        }
        index = 99;
        for (int i = 4; i >= 0; i--) {
            if (s1.length() > i) {
                double value = Double.parseDouble(s1.charAt(i) + "");
                if (value == 1) {
                    result[index - 4 + i] = 1;
                }
            }
            if (s2.length() > i) {
                double value = Double.parseDouble(s2.charAt(i) + "");
                if (value == 1) {
                    result[index - 9 + i] = 1;
                }
            }
            if (s3.length() > i) {
                double value = Double.parseDouble(s3.charAt(i) + "");
                if (value == 1) {
                    result[index - 14 + i] = 1;
                }
            }
            if (s4.length() > i) {
                double value = Double.parseDouble(s4.charAt(i) + "");
                if (value == 1) {
                    result[index - 19 + i] = 1;
                }
            }
        }
        return result;
    }

    @Override
    public double[] actualInfoLite() {
        double[] result = new double[6];

        Node left = getNodeByRotation(Rotation.left(lastRotation));
        if (isWall(left.getX(), left.getY()) || isBody(left.getX(), left.getY())) {
            result[0] = 1;
        }
        if (isAppleInDirection(left)) {
            result[3] = 1;
        }

        Node forward = getNodeByRotation(lastRotation);
        if (isWall(forward.getX(), forward.getY()) || isBody(forward.getX(), forward.getY())) {
            result[1] = 1;
        }
        if (isAppleInDirection(forward)) {
            result[4] = 1;
        }

        Node right = getNodeByRotation(Rotation.right(lastRotation));
        if (isWall(right.getX(), right.getY()) || isBody(right.getX(), right.getY())) {
            result[2] = 1;
        }
        if (isAppleInDirection(right)) {
            result[5] = 1;
        }

        return result;
    }

    private boolean isAppleInDirection(Node direction) {
        if (body.get(0).getY() > direction.getY() && apple.getY() <= direction.getY()) {
            return true;
        } else {
            if (body.get(0).getY() < direction.getY() && apple.getY() >= direction.getY()) {
                return true;
            } else {
                if (body.get(0).getX() > direction.getX() && apple.getX() <= direction.getX()) {
                    return true;
                } else {
                    if (body.get(0).getX() < direction.getX() && apple.getX() >= direction.getX()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Node getNodeByRotation(Rotation rotation) {
        switch (rotation) {
            case UP:
                return new Node(body.get(0).getX() - 1, body.get(0).getY());
            case RIGHT:
                return new Node(body.get(0).getX(), body.get(0).getY() + 1);
            case DOWN:
                return new Node(body.get(0).getX() + 1, body.get(0).getY());
            case LEFT:
                return new Node(body.get(0).getX(), body.get(0).getY() - 1);
        }
        return null;
    }

    private boolean move(Rotation rotation) {
        switch (rotation) {
            case UP:
                return move(-1, 0);
            case RIGHT:
                return move(0, 1);
            case DOWN:
                return move(1, 0);
            case LEFT:
                return move(0, -1);
        }
        return false;
    }

    private boolean move(int x, int y) {
        boolean helpScoreForApple = false;
        Node newHead = new Node(body.get(0).getX() + x, body.get(0).getY() + y);
        //if apple
        if (newHead.getX() == apple.getX() && newHead.getY() == apple.getY()) {
            apple = generateApple();
            score++;
            helpScore += 20;
            helpScoreForApple = true;
        } else {
            //remove last
            body.remove(body.size() - 1);
        }
        //if wall -> false
        if (newHead.getX() < 0 || newHead.getY() < 0 || newHead.getX() >= height || newHead.getY() >= width) {
            return false;
        }
        //if body -> false;
        if (isBody(newHead.getX(), newHead.getY())) {
            return false;
        }
        //create new head
        body.add(0, newHead);
        lastRotation = calculateLastRotation();
        if (!helpScoreForApple) {
            int tempHelpScore = calculateHelpScore();
            //move closer to apple
            if (lastHelpScore > tempHelpScore) {
                helpScore += 2;
                //move away from apple
            } else {
                helpScore -= 3;
            }
        }
        lastHelpScore = calculateHelpScore();
        return true;
    }

    private int calculateHelpScore() {
        return Math.abs(apple.getX() - body.get(0).getX()) + Math.abs(apple.getY() - body.get(0).getY());
    }

    private List<Node> generateBody() {
        List<Node> body = new ArrayList<>();
        Node head = new Node(height / 2, width / 2);
        Node tail1 = new Node(head.getX(), head.getY() - 1);
        Node tail2 = new Node(head.getX(), head.getY() - 2);
        body.add(head);
        body.add(tail1);
        body.add(tail2);
        return body;
    }

    private boolean isBody(int x, int y) {
        for (Node item : body) {
            if (x == item.getX() && y == item.getY()) {
                return true;
            }
        }
        return false;
    }

    //TODO check if x is width side and y is height side
    private boolean isWall(int x, int y) {
        return x < 0 || y < 0 || y >= width || x >= height;
    }

    private boolean isApple(int x, int y) {
        return x == apple.getX() && y == apple.getY();
    }

}
