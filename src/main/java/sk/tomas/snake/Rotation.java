package sk.tomas.snake;

public enum Rotation {
    UP, DOWN, LEFT, RIGHT;

    public static Rotation left(Rotation rotation) {
        switch (rotation) {
            case RIGHT:
                return UP;
            case DOWN:
                return RIGHT;
            case LEFT:
                return DOWN;
            case UP:
                return LEFT;
        }
        return null;
    }

    public static Rotation right(Rotation rotation) {
        switch (rotation) {
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            case UP:
                return RIGHT;
        }
        return null;
    }
}
