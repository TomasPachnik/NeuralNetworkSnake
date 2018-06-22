package sk.tomas.neural;

interface Network {

    void teach(double[] input, double[] expected) throws InputException;

}
