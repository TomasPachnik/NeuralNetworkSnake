package sk.tomas.snake;

import java.util.Random;

public enum Direction {
    LEFT, RIGHT, UP, DOWN;

    public static Direction getAtPosition(int position) {
        switch (position) {
            case 0:
                return UP;
            case 1:
                return DOWN;
            case 2:
                return LEFT;
            case 3:
                return RIGHT;
        }
        return UP;
    }

    public static Direction getRandomDirection() {
        Random random = new Random();
        return getAtPosition(random.nextInt(Direction.values().length));
    }

}
