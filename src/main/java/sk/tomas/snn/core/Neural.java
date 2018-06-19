package sk.tomas.snn.core;

import sk.tomas.snn.func.Func;

import java.util.ArrayList;
import java.util.List;

public class Neural {

    private double x0;
    private double w0;

    private List<NeuralInput> neuralInputs;

    public Neural(List<NeuralInput> neuralInputs) {
        this.neuralInputs = neuralInputs;
        x0 = 1;
        w0 = Func.randomInit();
    }

    public boolean calculate() {
        //Func.sigmoid();
        return false;
    }

    void randomizeWeights() {
        for (NeuralInput neuralInput : neuralInputs) {
            neuralInput.setW(Func.randomInit());
            System.out.println(neuralInput.getW());
        }
    }

    public List<NeuralInput> getNeuralInputs() {
        return neuralInputs;
    }
}
