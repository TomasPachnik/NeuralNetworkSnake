package sk.tomas.neural.math.activation;

public class None implements Activation {

    @Override
    public double calculate(double x) {
        return x;
    }
}
