package sk.tomas.snake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Body implements Serializable {

    private List<Node> body;
    private Board board;
    private SnakeImpl core;

    Body(SnakeImpl core, Board board, Node head) {
        this.board = board;
        this.core = core;
        body = new ArrayList<>();
        head.setSnakeBody(true);
        head.setHead(true);
        body.add(0, head);
        Node neck = board.getAtPosition(head.getX(), head.getY() - 1);
        neck.setSnakeBody(true);
        neck.setNeck(true);
        body.add(neck);
    }

    boolean move(Direction direction) {
        Node temp;
        switch (direction) {
            case LEFT:
                if (body.get(0).getY() > 0) {
                    temp = board.getAtPosition(body.get(0).getX(), body.get(0).getY() - 1);
                    return move(temp);
                }
                return false;
            case RIGHT:
                if (body.get(0).getY() < board.getGridHeight() - 1) {
                    temp = board.getAtPosition(body.get(0).getX(), body.get(0).getY() + 1);
                    return move(temp);
                }
                return false;
            case UP:
                if (body.get(0).getX() > 0) {
                    temp = board.getAtPosition(body.get(0).getX() - 1, body.get(0).getY());
                    return move(temp);
                }
                return false;
            case DOWN:
                if (body.get(0).getX() < board.getGridWidth() - 1) {
                    temp = board.getAtPosition(body.get(0).getX() + 1, body.get(0).getY());
                    return move(temp);
                }
                return false;
        }
        return false;
    }

    int wallLeft() {
        return getHead().getY();
    }

    int wallRight() {
        return board.getGridHeight() - getHead().getY() - 1;
    }

    int wallUp() {
        return getHead().getX();
    }

    int wallDown() {
        return board.getGridWidth() - getHead().getX() - 1;
    }

    int appleLeft() {
        int temp = getHead().getY();
        while (temp >= 0) {
            if (board.getAtPosition(getHead().getX(), temp).isApple()) {
                return getHead().getY() - temp;
            } else {
                temp--;
            }
        }
        return 0;
    }

    int appleRight() {
        int temp = getHead().getY();
        while (temp < board.getGridHeight()) {
            if (board.getAtPosition(getHead().getX(), temp).isApple()) {
                return temp - getHead().getY();
            } else {
                temp++;
            }
        }
        return 0;
    }

    int appleUp() {
        int temp = getHead().getX();
        while (temp >= 0) {
            if (board.getAtPosition(temp, getHead().getY()).isApple()) {
                return getHead().getX() - temp;
            } else {
                temp--;
            }
        }
        return 0;
    }

    int appleDown() {
        int temp = getHead().getX();
        while (temp < board.getGridWidth()) {
            if (board.getAtPosition(temp, getHead().getY()).isApple()) {
                return temp - getHead().getX();
            } else {
                temp++;
            }
        }
        return 0;
    }

    int bodyLeft() {
        if (getHead().getY() > 0 && board.getAtPosition(getHead().getX(), getHead().getY() - 1).isSnakeBody()) {
            return 1;
        }
        return 0;
    }

    int bodyRight() {
        if (getHead().getY() < board.getGridHeight() - 1 && board.getAtPosition(getHead().getX(), getHead().getY() + 1).isSnakeBody()) {
            return 1;
        }
        return 0;
    }

    int bodyUp() {
        if (getHead().getX() > 0 && board.getAtPosition(getHead().getX() - 1, getHead().getY()).isSnakeBody()) {
            return 1;
        }
        return 0;
    }

    int bodyDown() {
        if (getHead().getX() < board.getGridWidth() - 1 && board.getAtPosition(getHead().getX() + 1, getHead().getY()).isSnakeBody()) {
            return 1;
        }
        return 0;
    }

    Node getHead() {
        return body.get(0);
    }

    private boolean move(Node first) {
        if (first.isApple()) {
            first.setApple(false);
            core.setAppleAtRandomPosition(false);
            core.eatAppleScore();
        } else {
            Node last = body.get(body.size() - 1);
            last.setSnakeBody(false);
            body.remove(last);
        }
        if (!first.isSnakeBody()) {
            first.setSnakeBody(true);
            first.setHead(true);
            body.add(0, first);
            body.get(1).setHead(false);
            body.get(1).setNeck(true);
            if (body.size() > 2) {
                body.get(2).setNeck(false);
            }
            return true;
        }
        return false;
    }

    List<Node> getBody() {
        return body;
    }
}
