package sk.tomas.neural;

public class Main {

    public static void main(String[] args) throws InputException {
        Network network = new NetworkImpl();

        double[] input = new double[3];
        input[0] = 0;
        input[1] = 1;
        input[2] = 0;

        network.teach(input, input);
        System.out.println();
    }

}
