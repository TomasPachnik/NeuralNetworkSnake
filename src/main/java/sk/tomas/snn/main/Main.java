package sk.tomas.snn.main;

import sk.tomas.snn.core.NeuralNetwork;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.randomizeWeights();
        double result1 = 0;
        double result2 = 0;
        int number = 5000;
        for (int i = 0; i < number; i++) {
            result1 = neuralNetwork.run(0, 1, 0);
            result2 = neuralNetwork.run(1, 0, 1);
        }
        System.out.println(result1);
        System.out.println(result2);
    }

}
