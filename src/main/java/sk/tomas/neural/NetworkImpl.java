package sk.tomas.neural;

import java.util.ArrayList;
import java.util.List;

public class NetworkImpl implements Network {

    private List<List<Neural>> network;
    private double learningRate = 0.5;

    public NetworkImpl() {
        init();
        randomizeWeights();
        addBias();
        setUpWeightsManually();
    }

    private void init() {
        network = new ArrayList<>();
        for (int i = 0; i < Config.networkSize.length; i++) {
            List<Neural> layer = new ArrayList<>();
            for (int j = 0; j < Config.networkSize[i]; j++) {
                //input layer does not have ancestors
                List<NeuralInput> neuralInputs = new ArrayList<>();
                if (i < 1) {
                    neuralInputs.add(new NeuralInput(null));
                } else {
                    for (int k = 0; k < Config.networkSize[i - 1]; k++) {
                        neuralInputs.add(new NeuralInput(network.get(i - 1).get(k)));
                    }
                }
                layer.add(new Neural(neuralInputs));
            }
            network.add(layer);
        }
    }

    private void randomizeWeights() {
        for (List<Neural> innerList : network) {
            for (Neural neural : innerList) {
                neural.randomizeWeights();
            }
        }
    }

    private void addBias() {
        for (int i = 1; i < Config.networkSize.length; i++) {
            addBias(i);
        }
    }

    private void addBias(int layerNumber) {
        Neural bias = new Neural(new ArrayList<>());
        bias.setBias(true);
        bias.setLastValue(1);
        for (Neural neural : network.get(layerNumber)) {
            neural.addBias(bias);
        }

    }

    @Override
    public void teach(double[] input, double[] expected) throws InputException {
        if (Config.networkSize[0] != input.length) {
            throw new InputException("wrong input length");
        }
        for (int i = 0; i < network.get(0).size(); i++) {
            network.get(0).get(i).setLastValue(input[i]);
        }
        double[] run = run();
        for (double v : run) {
            System.out.print(v + " ");
        }
        System.out.println();
        backPropagation(run, expected);
    }

    private double[] run() {
        for (int i = 1; i < network.size(); i++) {
            for (Neural neural : network.get(i)) {
                for (NeuralInput neuralInput : neural.getInputs()) {
                    neuralInput.setX(neuralInput.getAncestor().getLastValue());
                }
                neural.forwardPropagation();
            }
        }
        double[] result = new double[Config.networkSize[Config.networkSize.length - 1]];
        for (int i = 0; i < result.length; i++) {
            result[i] = network.get(network.size() - 1).get(i).getLastValue();
        }
        return result;
    }

    private void backPropagation(double[] result, double[] expected) {
        //double errorRate = Util.squaredError(expected, result);
        //System.out.println(errorRate);
/*
        List<Double> newWeights = new ArrayList<>();

        for (int i = 0; i < expected.length; i++) {
            for (Neural neural : network.get(network.size() - 2)) {
                double temp = Util.errorAffect(expected[i], result[i], neural.getLastValue());
                Util.calculateNewWeight(network.get(2).get(0).getInputs().get(0).getW(), learningRate, temp);
            }
        }
        newWeights.forEach(System.out::println);

        double o1 = Util.errorAffect(expected[0], result[0], network.get(1).get(0).getLastValue());
        o1 = Util.calculateNewWeight(network.get(2).get(0).getInputs().get(0).getW(), learningRate, o1);
        //System.out.println(o1);
*/
        //last layer
        //for every neural
        //for every input
        //expected, actual, last value of neural before, weight, learning rate
        for (int i = 0; i < expected.length; i++) {
            Neural neural = network.get(network.size() - 1).get(i);
            for (NeuralInput input : neural.getInputs()) {
                if (!input.getAncestor().isBias()) {
                    double temp = Util.errorAffect(expected[i], neural.getLastValue(), input.getAncestor().getLastValue());
                    temp = Util.calculateNewWeight(input.getW(), learningRate, temp);
                    System.out.println(temp);
                }
            }
        }
    }

    private void setUpWeightsManually() {
        network.get(1).get(0).getInputs().get(0).setW(0.15);
        network.get(1).get(0).getInputs().get(1).setW(0.2);
        network.get(1).get(1).getInputs().get(0).setW(0.25);
        network.get(1).get(1).getInputs().get(1).setW(0.3);

        network.get(2).get(0).getInputs().get(0).setW(0.4);
        network.get(2).get(0).getInputs().get(1).setW(0.45);
        network.get(2).get(1).getInputs().get(0).setW(0.5);
        network.get(2).get(1).getInputs().get(1).setW(0.55);

        network.get(1).get(0).getInputs().get(2).setW(0.35);
        network.get(1).get(1).getInputs().get(2).setW(0.35);
        network.get(2).get(0).getInputs().get(2).setW(0.6);
        network.get(2).get(1).getInputs().get(2).setW(0.6);
    }

}
