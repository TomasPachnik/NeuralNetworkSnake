package sk.tomas.snn.func;

import sk.tomas.snake.Direction;

public class Func {

    /**
     * calculate sigmoid function for x
     *
     * @param x
     * @return
     */
    public static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    /**
     * return number between -1 and 1
     *
     * @return
     */
    public static double randomInit() {
        return ((Math.random() * 2) - 1);
    }

    public static boolean round(double x) {
        return x >= 0.5;
    }

    public static double[] mistake(double[] calculation, double[] expected) {
        double[] result = new double[4];
        for (int i = 0; i < 4; i++) {
            result[i] = expected[i] - calculation[i];
        }
        return result;
    }

    public static double calculateMistake(double[] calculation, double[] expected) {
        double[] mistake = mistake(calculation, expected);
        double result = 0;
        int count = 1;
        int total = 1;
        for (double aMistake : mistake) {
            result += Math.abs(aMistake * count);
            count *= 2;
            total += count;
        }
        return result / total;
    }


    public static double convert(boolean x) {
        if (x) {
            return 1;
        } else {
            return 0;
        }
    }

    public static double[] expected(Direction direction) {
        switch (direction) {
            case UP:
                return new double[]{1, 0, 0, 0};
            case DOWN:
                return new double[]{0, 1, 0, 0};
            case LEFT:
                return new double[]{0, 0, 1, 0};
            case RIGHT:
                return new double[]{0, 0, 0, 1};
        }
        return new double[]{0, 0, 0, 0};
    }

    public static Direction calcDirection(double[] result) {
        double temp = -999;
        for (double aResult : result) {
            if (temp < aResult) {
                temp = aResult;
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i] == temp) {
                return Direction.getAtPosition(i);
            }
        }
        return Direction.UP;
    }

}
