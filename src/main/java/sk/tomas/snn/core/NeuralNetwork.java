package sk.tomas.snn.core;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private List<List<Neural>> network;

    private int[] config = {2, 2, 1};

    public NeuralNetwork() {
        init();
    }

    public int run(int first, double second) {
        for (Neural neural : network.get(0)) {
            neural.getNeuralInputs().get(0).setX(first);
            neural.getNeuralInputs().get(1).setX(second);
        }

        return 0;
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

}
