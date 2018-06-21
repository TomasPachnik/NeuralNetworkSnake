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
                break;
            case RIGHT:
                if (body.get(0).getY() < board.getGridHeight()) {
                    temp = board.getAtPosition(body.get(0).getX(), body.get(0).getY() + 1);
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                break;
            case UP:
                if (body.get(0).getX() > 0) {
                    temp = board.getAtPosition(body.get(0).getX() - 1, body.get(0).getY());
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                break;
            case DOWN:
                if (body.get(0).getX() < board.getGridWidth()) {
                    temp = board.getAtPosition(body.get(0).getX() + 1, body.get(0).getY());
                    if (!temp.isSnakeBody()) {
                        move(temp);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private void move(Node first) {
        System.out.println(first);
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
