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

        double[] a = new double[]{0, 1, 0};
        double[] b = new double[]{0.228, 0.619, 0.153};
        System.out.println(Util.crossEntropy(a, b));

    }

}
