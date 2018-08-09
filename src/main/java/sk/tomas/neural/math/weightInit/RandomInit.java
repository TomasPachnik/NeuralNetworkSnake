package sk.tomas.neural.math.weightInit;

public class RandomInit implements WeightInit {

    @Override
    public double initWeight() {
        return ((Math.random() * 2) - 1);
    }
}
