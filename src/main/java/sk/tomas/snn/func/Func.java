package sk.tomas.snn.func;

public class Func {

    public static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

}
