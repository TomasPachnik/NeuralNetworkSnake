package sk.tomas.neural.config;

import java.util.HashMap;
import java.util.Map;
import sk.tomas.neural.config.enums.Activation;
import sk.tomas.neural.config.enums.WeightInit;
import sk.tomas.neural.model.NeuralNetworkModel;

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
        NeuralNetworkModel model = new NeuralNetworkModel();

        //TODO implement
        return model;

    }


}
