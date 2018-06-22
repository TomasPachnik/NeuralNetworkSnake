package sk.tomas.neural;

import java.io.Serializable;
import java.util.List;

public class Neural implements Serializable {

    private List<NeuralInput> inputs;
    private double lastValue;

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
        neuralInput.setX(1);
        neuralInput.setW(Util.randomInit());
        inputs.add(neuralInput);
    }

    //TODO implement
    void backPropagation() {

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
}
