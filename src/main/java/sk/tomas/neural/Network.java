package sk.tomas.neural;

public interface Network {

    double[] teach(double[] input, double[] expected) throws InputException;

    double[] run(double[] input) throws InputException;

}
