package sk.tomas.neural;

import java.io.Serializable;
import java.util.List;

class NeuralOld implements Serializable {

    private List<NeuralInputOld> inputs;
    private double lastValue;
    private boolean bias;

    NeuralOld(List<NeuralInputOld> inputs) {
        this.inputs = inputs;
    }

    void randomizeWeights() {
        for (NeuralInputOld neuralInput : inputs) {
            neuralInput.setW(Util.randomInit());
        }
    }

    void forwardPropagation() {
        lastValue = 0;
        for (NeuralInputOld input : inputs) {
            lastValue += input.getX() * input.getW();
        }
        lastValue = Util.sigmoid(lastValue);
    }

    void addBias(NeuralOld bias) {
        NeuralInputOld neuralInput = new NeuralInputOld(bias);
        neuralInput.setW(Util.randomInit());
        inputs.add(neuralInput);
    }

    double getLastValue() {
        return lastValue;
    }

    void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    List<NeuralInputOld> getInputs() {
        return inputs;
    }

    boolean isBias() {
        return bias;
    }

    void setBias() {
        this.bias = true;
    }
}
