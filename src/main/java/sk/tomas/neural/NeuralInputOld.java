package sk.tomas.neural;

import java.io.Serializable;

class NeuralInputOld implements Serializable {

    private double x;
    private double w;
    private NeuralOld ancestor;

    NeuralInputOld(NeuralOld ancestor) {
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

    NeuralOld getAncestor() {
        return ancestor;
    }

    void setAncestor(NeuralOld ancestor) {
        this.ancestor = ancestor;
    }
}
