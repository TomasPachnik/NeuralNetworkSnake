package sk.tomas.snn.core;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private List<List<Neural>> network;

    private int[] config = {2, 2, 1};

    public NeuralNetwork() {
        init();
        demo();
        System.out.println();
    }

    public double run(int first, double second) {
        network.get(0).get(0).setLastResult(first);
        network.get(0).get(1).setLastResult(second);

        for (int i = 1; i < network.size(); i++) {
            for (Neural neural : network.get(i)) {
                for (NeuralInput neuralInput : neural.getNeuralInputs()) {
                    neuralInput.setX(neuralInput.getNeural().getLastResult());
                }
                neural.calculate();
            }
        }
        return network.get(network.size() - 1).get(0).getLastResult();
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

    public void demo() {
        network.get(1).get(0).getNeuralInputs().get(0).setW(0.62);
        network.get(1).get(0).getNeuralInputs().get(1).setW(0.55);
        network.get(1).get(1).getNeuralInputs().get(0).setW(0.42);
        network.get(1).get(1).getNeuralInputs().get(1).setW(-0.17);
        network.get(2).get(0).getNeuralInputs().get(0).setW(0.35);
        network.get(2).get(0).getNeuralInputs().get(1).setW(0.81);
    }

}
