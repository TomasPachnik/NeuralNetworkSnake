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

    void forwardPropagation() {
        double number = 0;
        for (NeuralInput neuralInput : neuralInputs) {
            number += (neuralInput.getX() * neuralInput.getW());
        }
        number += (x0 * w0);
        lastResult = Func.sigmoid(number);
    }

    void backPropagation(double learningRate, double mistake) {
        for (NeuralInput neuralInput : neuralInputs) {
            double value = learningRate * mistake * neuralInput.getNeural().getLastResult() * lastResult * (1 - lastResult);
            neuralInput.setW(neuralInput.getW() + value);
        }
        //bias
        double value = learningRate * mistake * x0 * lastResult * (1 - lastResult);
        w0 += value;
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
