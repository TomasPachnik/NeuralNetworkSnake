package sk.tomas.snake;

import java.util.Random;

public enum Movement {
    LEFT, FORWARD, RIGHT;

    private static Random random;

    static {
        random = new Random();
    }

    public static Movement random() {
        return getAtPosition(random.nextInt(3));
    }

    public static Movement map(double[] array) {
        double max = Math.max(Math.max(array[0], array[1]), array[2]);
        int position = 0;
        for (int i = 0; i < 3; i++) {
            if (array[i] == max) {
                position = i;
                break;
            }
        }
        return getAtPosition(position);
    }

    public static double[] randomBinary() {
        return map(random());
    }


    public static double[] map(Movement movement) {
        switch (movement) {
            case LEFT:
                return new double[]{1, 0, 0};
            case FORWARD:
                return new double[]{0, 1, 0};
            case RIGHT:
                return new double[]{0, 0, 1};
        }
        return null;
    }

    private static Movement getAtPosition(int position) {
        switch (position) {
            case 0:
                return LEFT;
            case 1:
                return FORWARD;
            case 2:
                return RIGHT;
        }
        return null;
    }

}
