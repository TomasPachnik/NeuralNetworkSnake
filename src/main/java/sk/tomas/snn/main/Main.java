package sk.tomas.snn.main;

import sk.tomas.snn.core.NeuralNetwork;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.randomizeWeights();
        int number = 5000;
        for (int i = 0; i < number; i++) {
            neuralNetwork.teach(7, 3, true);
            neuralNetwork.teach(1, 0, false);
        }
        System.out.println(neuralNetwork.calculate(7, 2));
        System.out.println(neuralNetwork.calculate(2, 0));
    }

}
