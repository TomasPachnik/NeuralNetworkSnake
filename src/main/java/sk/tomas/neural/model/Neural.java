package sk.tomas.neural.model;

import java.io.Serializable;
import java.util.List;
import sk.tomas.neural.math.activation.Activation;
import sk.tomas.neural.math.weightInit.WeightInit;

public class Neural implements Serializable {

    private int id;
    private List<NeuralInput> inputs;
    private double lastValue;
    private boolean bias;
    private Activation activation;
    private WeightInit weightInit;

    public Neural(int id, List<NeuralInput> inputs, WeightInit weightInit, Activation activation) {
        this.id = id;
        this.inputs = inputs;
        this.weightInit = weightInit;
        this.activation = activation;
    }

    public void setBias(boolean bias) {
        this.bias = bias;
    }

    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    void forwardPropagation() {
        double temp = 0;
        for (NeuralInput input : inputs) {
            temp += input.getX() * input.getW();
        }
        lastValue = activation.calculate(temp);
    }

    public void addBias(Neural bias) {
        NeuralInput neuralInput = new NeuralInput(bias);
        //TODO init weights
        //neuralInput.setW(Util.randomInit());
        inputs.add(neuralInput);
    }

    public void setWeight() {
        for (NeuralInput neuralInput : inputs) {
            neuralInput.setW(weightInit.initWeight());
        }
    }

    List<NeuralInput> getInputs() {
        return inputs;
    }

    double getLastValue() {
        return lastValue;
    }
}
