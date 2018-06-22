package sk.tomas.neural;

class Util {

    static double randomInit() {
        return ((Math.random() * 2) - 1);
    }

    static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    static double[] softMax(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.exp(array[i]);
        }
        double sum = 0;
        for (double v : array) {
            sum += v;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] / sum;
        }
        return array;
    }

}
