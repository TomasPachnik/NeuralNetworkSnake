package sk.tomas.snn.core;

import java.io.Serializable;

class NeuralInput implements Serializable {

    private double x;
    private double w;
    private Neural neural;

    NeuralInput() {
    }

    NeuralInput(Neural neural) {
        this.neural = neural;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getW() {
        return w;
    }

    void setW(double w) {
        this.w = w;
    }

    Neural getNeural() {
        return neural;
    }

}
