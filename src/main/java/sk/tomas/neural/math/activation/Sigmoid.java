package sk.tomas.neural.math.activation;

public class Sigmoid implements Activation {

    @Override
    public double calculate(double x) {
        return (1 / (1 + Math.exp(-x)));
    }
}
