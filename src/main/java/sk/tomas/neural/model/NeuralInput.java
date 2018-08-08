package sk.tomas.neural.model;

public class NeuralInput {

    private double x;
    private double w;
    private Neural ancestor;

    public NeuralInput(Neural ancestor) {
        this.ancestor = ancestor;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public Neural getAncestor() {
        return ancestor;
    }

}
