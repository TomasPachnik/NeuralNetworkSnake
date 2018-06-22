package sk.tomas.snake;

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

}
