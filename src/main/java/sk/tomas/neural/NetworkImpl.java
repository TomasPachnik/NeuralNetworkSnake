package sk.tomas.neural;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NetworkImpl implements Network, Serializable {

    private int[] networkSize;
    private double learningRate;
    private double errorRate;
    private int cycles;

    private List<List<Neural>> network;

    public NetworkImpl(int inputLayer, int hiddenLayer, int outpuLayer, double learningRate) {
        this.networkSize = new int[]{inputLayer, hiddenLayer, outpuLayer};
        this.learningRate = learningRate;
        init();
        randomizeWeights();
        addBias();
    }

    private void init() {
        network = new ArrayList<>();
        for (int i = 0; i < networkSize.length; i++) {
            List<Neural> layer = new ArrayList<>();
            for (int j = 0; j < networkSize[i]; j++) {
                //input layer does not have ancestors
                List<NeuralInput> neuralInputs = new ArrayList<>();
                if (i < 1) {
                    neuralInputs.add(new NeuralInput(null));
                } else {
                    for (int k = 0; k < networkSize[i - 1]; k++) {
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
        for (int i = 1; i < networkSize.length; i++) {
            addBias(i);
        }
    }

    private void addBias(int layerNumber) {
        Neural bias = new Neural(new ArrayList<>());
        bias.setBias();
        bias.setLastValue(1);
        for (Neural neural : network.get(layerNumber)) {
            neural.addBias(bias);
        }
    }

    @Override
    public Network getClone() {
        return (Network) Util.clone(this);
    }

    @Override
    public double getErrorRate() {
        return errorRate / cycles;
    }

    @Override
    public double[] teach(double[] input, double[] expected) throws InputException {
        double[] run = run(input);
        backPropagation(expected);
        cycles++;
        errorRate += Util.squaredError(expected, run);
        return run;
    }

    @Override
    public double[] run(double[] input) throws InputException {
        if (networkSize[0] != input.length) {
            throw new InputException("wrong input length");
        }
        for (int i = 0; i < network.get(0).size(); i++) {
            network.get(0).get(i).setLastValue(input[i]);
        }
        return run();
    }

    @Override
    public void saveState(String filename) throws FileException {
        Util.writeFile(filename, this);
    }

    @Override
    public void loadState(String filename) throws FileException {
        NetworkImpl obj = (NetworkImpl) Util.readFile(filename);
        network = obj.getNetwork();
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
        double[] result = new double[networkSize[networkSize.length - 1]];
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

    private List<List<Neural>> getNetwork() {
        return network;
    }
}
