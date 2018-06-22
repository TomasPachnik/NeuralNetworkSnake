package sk.tomas.snake;

import java.io.Serializable;

class Node implements Serializable {

    private int x;
    private int y;
    private boolean isSnakeBody;
    private boolean isApple;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    boolean isSnakeBody() {
        return isSnakeBody;
    }

    void setSnakeBody(boolean snakeBody) {
        this.isSnakeBody = snakeBody;
    }

    boolean isApple() {
        return isApple;
    }

    void setApple(boolean apple) {
        isApple = apple;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", isSnakeBody=" + isSnakeBody +
                ", isApple=" + isApple +
                '}';
    }
}
