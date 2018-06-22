package sk.tomas.neural;

import java.io.Serializable;

class NeuralInput implements Serializable {

    private double x;
    private double w;
    private Neural ancestor;

    public NeuralInput(Neural ancestor) {
        this.ancestor = ancestor;
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

    Neural getAncestor() {
        return ancestor;
    }

    void setAncestor(Neural ancestor) {
        this.ancestor = ancestor;
    }
}
