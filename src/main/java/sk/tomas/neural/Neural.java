package sk.tomas.neural;

import java.io.Serializable;
import java.util.List;

class Neural implements Serializable {

    private List<NeuralInput> inputs;
    private double lastValue;
    private boolean bias;

    Neural(List<NeuralInput> inputs) {
        this.inputs = inputs;
    }

    void randomizeWeights() {
        for (NeuralInput neuralInput : inputs) {
            neuralInput.setW(Util.randomInit());
        }
    }

    void forwardPropagation() {
        lastValue = 0;
        for (NeuralInput input : inputs) {
            lastValue += input.getX() * input.getW();
        }
        lastValue = Util.sigmoid(lastValue);
    }

    void addBias(Neural bias) {
        NeuralInput neuralInput = new NeuralInput(bias);
        neuralInput.setW(Util.randomInit());
        inputs.add(neuralInput);
    }

    double getLastValue() {
        return lastValue;
    }

    void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    List<NeuralInput> getInputs() {
        return inputs;
    }

    boolean isBias() {
        return bias;
    }

    void setBias() {
        this.bias = true;
    }
}
