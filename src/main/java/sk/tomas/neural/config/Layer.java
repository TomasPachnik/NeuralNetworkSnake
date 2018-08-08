package sk.tomas.neural.config;

import sk.tomas.neural.config.enums.Activation;

public class Layer {

    private int neuronsNumber;
    private boolean bias;
    private Activation activationFunction;

    public Layer(int neuronsNumber, boolean bias, Activation activationFunction) {
        this.neuronsNumber = neuronsNumber;
        this.bias = bias;
        this.activationFunction = activationFunction;
    }

    public int getNeuronsNumber() {
        return neuronsNumber;
    }

    public boolean isBias() {
        return bias;
    }

    public Activation getActivationFunction() {
        return activationFunction;
    }
}
