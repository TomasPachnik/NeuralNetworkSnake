package sk.tomas.snn.core;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private List<List<Neural>> network;

    private double learningRate = 0.25;
    private double netLastResult;
    private int[] config = {2, 2, 1};

    public NeuralNetwork() {
        init();
    }

    public double run(int first, double second, int expected) {
        network.get(0).get(0).setLastResult(first);
        network.get(0).get(1).setLastResult(second);

        for (int i = 1; i < network.size(); i++) {
            for (Neural neural : network.get(i)) {
                for (NeuralInput neuralInput : neural.getNeuralInputs()) {
                    neuralInput.setX(neuralInput.getNeural().getLastResult());
                }
                neural.forwardPropagation();
            }
        }
        netLastResult = network.get(network.size() - 1).get(0).getLastResult();
        backPropagation(expected - netLastResult);
        return netLastResult;
    }

    public void randomizeWeights() {
        for (List<Neural> innerList : network) {
            for (Neural neural : innerList) {
                neural.randomizeWeights();
            }
        }
    }

    private void init() {
        network = new ArrayList<>();
        for (int i = 0; i < config.length; i++) {
            List<Neural> item = new ArrayList<>();
            for (int j = 0; j < config[i]; j++) {
                if (i < 1) {
                    List<NeuralInput> neuralInputs = new ArrayList<>();
                    neuralInputs.add(new NeuralInput());
                    item.add(new Neural(neuralInputs));
                } else {
                    List<NeuralInput> neuralInputs = new ArrayList<>();
                    for (int k = 0; k < config[i - 1]; k++) {
                        neuralInputs.add(new NeuralInput(network.get(i - 1).get(k)));
                    }
                    item.add(new Neural(neuralInputs));
                }
            }
            network.add(item);
        }
    }

    private void backPropagation(double mistake) {
        for (int i = network.size() - 1; i >= 1; i--) {
            for (Neural neural : network.get(i)) {
                neural.backPropagation(learningRate, mistake);
            }
        }
    }
}
