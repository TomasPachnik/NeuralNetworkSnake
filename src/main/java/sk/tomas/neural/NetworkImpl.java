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
        backPropagation(expected);
        System.out.println(Util.squaredError(expected, run));
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

    private void backPropagation(double[] expected) {
        //last layer
        List<Double> lastLayer = new ArrayList<>();
        for (int i = 0; i < expected.length; i++) {
            Neural neural = network.get(network.size() - 1).get(i);
            for (NeuralInput input : neural.getInputs()) {
                if (!input.getAncestor().isBias()) {
                    double temp = Util.errorAffect(expected[i], neural.getLastValue(), input.getAncestor().getLastValue());
                    lastLayer.add(Util.calculateNewWeight(input.getW(), learningRate, temp));
                }
            }
        }
        //hidden layer
        List<Double> hiddenLayer = new ArrayList<>();
        for (int i = 0; i < expected.length; i++) {
            double mistake = calculateOutputMistake(i, expected);
            Neural neural = network.get(1).get(i);
            for (NeuralInput input : neural.getInputs()) {
                if (!input.getAncestor().isBias()) {
                    double temp = Util.errorAffectWithMistake(mistake, neural.getLastValue(), input.getAncestor().getLastValue());
                    hiddenLayer.add(Util.calculateNewWeight(input.getW(), learningRate, temp));
                }
            }
        }
        //setting calculated weights
        int index = 0;
        for (int i = 0; i < expected.length; i++) {
            Neural neural = network.get(network.size() - 1).get(i);
            for (NeuralInput input : neural.getInputs()) {
                if (!input.getAncestor().isBias()) {
                    input.setW(lastLayer.get(index));
                    index++;
                }
            }
        }
        index = 0;
        for (int i = 0; i < expected.length; i++) {
            Neural neural = network.get(1).get(i);
            for (NeuralInput input : neural.getInputs()) {
                if (!input.getAncestor().isBias()) {
                    input.setW(hiddenLayer.get(index));
                    index++;
                }
            }
        }
    }

    private double calculateOutputMistake(int index, double[] expected) {
        double count = 0;
        for (int j = 0; j < network.get(2).size(); j++) {
            double value = Util.outputError(expected[j], network.get(2).get(j).getLastValue()) * Util.outputInput(network.get(2).get(j).getLastValue());
            count += value * network.get(2).get(j).getInputs().get(index).getW();
        }
        return count;
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
