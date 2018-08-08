package sk.tomas.neural.model;

import java.util.List;
import sk.tomas.neural.math.activation.Activation;
import sk.tomas.neural.math.weightInit.WeightInit;

public class Neural {

    private List<NeuralInput> inputs;
    private double lastValue;
    private boolean bias;
    private Activation activation;
    private WeightInit weightInit;


    void forwardPropagation() {
        double temp = 0;
        for (NeuralInput input : inputs) {
            temp += input.getX() * input.getW();
        }
        lastValue = activation.calculate(temp);
    }

    void addBias(Neural bias) {
        NeuralInput neuralInput = new NeuralInput(bias);
        //TODO init weights
        //neuralInput.setW(Util.randomInit());
        inputs.add(neuralInput);
    }

    void randomizeWeights() {
        for (NeuralInput neuralInput : inputs) {
            neuralInput.setW(weightInit.initWeight());
        }
    }

}
