package sk.tomas.neural.math.weightInit;

public class Random implements WeightInit {

    @Override
    public double initWeight() {
        return ((Math.random() * 2) - 1);
    }
}
