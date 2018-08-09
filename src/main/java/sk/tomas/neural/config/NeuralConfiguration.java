package sk.tomas.neural.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sk.tomas.neural.config.enums.Activation;
import sk.tomas.neural.config.enums.WeightInit;
import sk.tomas.neural.math.activation.None;
import sk.tomas.neural.math.activation.Relu;
import sk.tomas.neural.math.activation.Sigmoid;
import sk.tomas.neural.math.weightInit.One;
import sk.tomas.neural.math.weightInit.RandomInit;
import sk.tomas.neural.math.weightInit.Zero;
import sk.tomas.neural.model.Neural;
import sk.tomas.neural.model.NeuralInput;
import sk.tomas.neural.model.NeuralNetworkModel;
import sk.tomas.neural.model.NeuralNetworkModelImpl;

public class NeuralConfiguration {

    //TODO implement
    private Long seed;
    private WeightInit weightInit;
    private boolean backpropagation;
    private Map<Integer, Layer> layers;

    public NeuralConfiguration() {
        layers = new HashMap<>();
    }

    public NeuralConfiguration initSeed(Long seed) {
        this.seed = seed;
        return this;
    }

    public NeuralConfiguration initWeight(WeightInit weightInit) {
        this.weightInit = weightInit;
        return this;
    }

    public NeuralConfiguration addLayer(int index, int neuronsNumber, boolean bias, Activation activationFunction) {
        this.layers.put(index, new Layer(neuronsNumber, bias, activationFunction));
        return this;
    }

    public NeuralConfiguration backpropagation(boolean backpropagation) {
        this.backpropagation = backpropagation;
        return this;
    }

    public NeuralNetworkModel build() {
        List<List<Neural>> network = initModel();
        network = initWeights(network);
        return new NeuralNetworkModelImpl(network);

    }

    private List<List<Neural>> initWeights(List<List<Neural>> model) {
        for (List<Neural> list : model) {
            for (Neural neural : list) {
                neural.setWeight();
            }
        }
        return model;
    }

    private List<List<Neural>> initModel() {
        List<List<Neural>> network = new ArrayList<>();
        for (int i = 0; i < layers.size(); i++) {
            List<Neural> layer = new ArrayList<>();
            for (int j = 0; j < layers.get(i).getNeuronsNumber(); j++) {
                //input layer does not have ancestors
                List<NeuralInput> neuralInputs = new ArrayList<>();
                if (i < 1) {
                    neuralInputs.add(new NeuralInput(null));
                } else {
                    for (int k = 0; k < layers.get(i - 1).getNeuronsNumber(); k++) {
                        neuralInputs.add(new NeuralInput(network.get(i - 1).get(k)));
                    }
                }
                layer.add(new Neural(j, neuralInputs, map(weightInit), map(layers.get(i).getActivationFunction())));
            }
            if (layers.get(i).isBias()) {
                layer = addBias(layer, layers.get(i));
            }
            network.add(layer);
        }
        return network;
    }

    private List<Neural> addBias(List<Neural> layer, Layer confLayer) {
        Neural bias = new Neural(-1, new ArrayList<>(), map(weightInit), map(confLayer.getActivationFunction()));
        bias.setBias(true);
        bias.setWeight();
        bias.setLastValue(1);
        for (Neural neural : layer) {
            neural.addBias(bias);
        }
        return layer;
    }

    private sk.tomas.neural.math.weightInit.WeightInit map(WeightInit enumValue) {
        switch (enumValue) {
            case ZERO:
                return new Zero();
            case ONE:
                return new One();
            case RANDOM:
            default:
                return new RandomInit();
        }
    }

    private sk.tomas.neural.math.activation.Activation map(Activation activation) {
        switch (activation) {
            case RELU:
                return new Relu();
            case SIGMOID:
                return new Sigmoid();
            case NONE:
            default:
                return new None();
        }
    }


}
