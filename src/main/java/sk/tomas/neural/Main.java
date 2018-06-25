package sk.tomas.neural;

public class Main {

    public static void main(String[] args) throws InputException {
        Network network = new NetworkImpl();

        double[] input = new double[2];
        double[] output = new double[2];

        input[0] = .05;
        input[1] = .1;
        output[0] = .01;
        output[1] = .99;

        network.teach(input, output);
        System.out.println();
    }
}
