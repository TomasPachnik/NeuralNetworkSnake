package sk.tomas.snn.core;

import sk.tomas.snn.func.Func;

import java.util.ArrayList;
import java.util.List;

class Neural {

    private double x0;
    private double w0;
    private double lastResult;

    private List<NeuralInput> neuralInputs;

    Neural(List<NeuralInput> neuralInputs) {
        this.neuralInputs = neuralInputs;
        x0 = 1;
        w0 = Func.randomInit();
    }

    void calculate() {
        double number = 0;
        for (NeuralInput neuralInput : neuralInputs) {
            number += neuralInput.getX() * neuralInput.getW();
        }
        lastResult = Func.sigmoid(number);
    }

    void backPropagation(double learningRate, double netLastResult) {
        for (NeuralInput neuralInput : neuralInputs) {
            double value = learningRate * netLastResult * neuralInput.getNeural().getLastResult() * lastResult * (1 - lastResult);
            neuralInput.setW(neuralInput.getW() + value);
        }
    }

    void randomizeWeights() {
        for (NeuralInput neuralInput : neuralInputs) {
            neuralInput.setW(Func.randomInit());
        }
    }

    List<NeuralInput> getNeuralInputs() {
        return neuralInputs;
    }

    void setLastResult(double lastResult) {
        this.lastResult = lastResult;
    }

    double getLastResult() {
        return lastResult;
    }
}
