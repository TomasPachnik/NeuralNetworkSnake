package sk.tomas.neural;

class Util {

    static double randomInit() {
        return ((Math.random() * 2) - 1);
    }

    static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

}
