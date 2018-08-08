package sk.tomas.neural.math.activation;

public class Relu implements Activation {

    @Override
    public double calculate(double x) {
        if (x <= 0) {
            return 0;
        }
        return 1;
    }
}
