package sk.tomas.ga;

import sk.tomas.neural.InputException;
import sk.tomas.neural.Network;

import java.util.Random;

class Individual {

    private Network network;
    private double sumScore; //sum of network scores
    private int runs; //number of network calculations

    public Individual(Network network) {
        this.network = network;
    }

    public double getFitness() {
        return sumScore / runs;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    public void run() throws InputException {
        Random random = new Random();
        double[] input = new double[network.getInputLayer()];
        for (int i = 0; i < input.length; i++) {
            if (random.nextBoolean()) {
                input[i] = 1;
            }
        }
        double[] output = network.run(input);
        rate(input, output);
        runs++;
    }

    private void rate(double[] input, double[] output) {
        if (input[0] == 0 && input[1] == 0 && output[0] < 0.5) {
            sumScore += 10;
        }
        if (input[0] == 0 && input[1] == 0 && output[0] < 0.5) {
            sumScore += 10;
        }
        if (input[0] == 1 && input[1] == 0 && output[0] > 0.5) {
            sumScore += 10;
        }
        if (input[0] == 1 && input[1] == 1 && output[0] > 0.5) {
            sumScore += 10;
        }
    }

}
