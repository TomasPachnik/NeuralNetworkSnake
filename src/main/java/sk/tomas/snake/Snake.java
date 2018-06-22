package sk.tomas.snake;

import java.util.ArrayList;
import java.util.List;

class Snake {

    private List<Node> body;
    private Board board;
    private Core core;

    Snake(Core core, Board board, Node head) {
        this.board = board;
        this.core = core;
        body = new ArrayList<>();
        body.add(0, head);
    }

    boolean move(Direction direction) {
        Node temp;
        switch (direction) {
            case LEFT:
                if (body.get(0).getY() > 0) {
                    temp = board.getAtPosition(body.get(0).getX(), body.get(0).getY() - 1);
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                return false;
            case RIGHT:
                if (body.get(0).getY() < board.getGridHeight()) {
                    temp = board.getAtPosition(body.get(0).getX(), body.get(0).getY() + 1);
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                return false;
            case UP:
                if (body.get(0).getX() > 0) {
                    temp = board.getAtPosition(body.get(0).getX() - 1, body.get(0).getY());
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                return false;
            case DOWN:
                if (body.get(0).getX() < board.getGridWidth()) {
                    temp = board.getAtPosition(body.get(0).getX() + 1, body.get(0).getY());
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                return false;
        }
        return false;
    }

    public int wallLeft(Node node) {
        return node.getY();
    }

    public int wallRight(Node node) {
        return board.getGridHeight() - node.getY() - 1;
    }

    public int wallUp(Node node) {
        return node.getX();
    }

    public int wallDown(Node node) {
        return board.getGridWidth() - node.getX() - 1;
    }

    public int appleLeft(Node node) {
        int temp = node.getY();
        while (temp >= 0) {
            if (board.getAtPosition(node.getX(), temp).isApple()) {
                return node.getY() - temp;
            } else {
                temp--;
            }
        }
        return 0;
    }

    public int appleRight(Node node) {
        int temp = node.getY();
        while (temp < board.getGridHeight()) {
            if (board.getAtPosition(node.getX(), temp).isApple()) {
                return temp - node.getY();
            } else {
                temp++;
            }
        }
        return 0;
    }

    public int appleUp(Node node) {
        int temp = node.getX();
        while (temp >= 0) {
            if (board.getAtPosition(temp, node.getY()).isApple()) {
                return node.getX() - temp;
            } else {
                temp--;
            }
        }
        return 0;
    }

    public int appleDown(Node node) {
        int temp = node.getX();
        while (temp < board.getGridWidth()) {
            if (board.getAtPosition(temp, node.getY()).isApple()) {
                return temp - node.getX();
            } else {
                temp++;
            }
        }
        return 0;
    }

    private void move(Node first) {
        first.setSnakeBody(true);
        body.add(0, first);
        if (first.isApple()) {
            first.setApple(false);
            core.setAppleAtRandomPosition();
        } else {
            Node last = body.get(body.size() - 1);
            last.setSnakeBody(false);
            body.remove(last);
        }
    }


}
