package sk.tomas.neural.math.activation;

import java.io.Serializable;

public interface Activation extends Serializable {

    double calculate(double x);

}
