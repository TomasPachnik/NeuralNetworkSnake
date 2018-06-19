package sk.tomas.snn.core;

public class NeuralInput {

    private double x;
    private double w;
    private Neural neural;

    public NeuralInput() {
    }

    public NeuralInput(Neural neural) {
        this.neural = neural;
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

    public Neural getNeural() {
        return neural;
    }

    public void setNeural(Neural neural) {
        this.neural = neural;
    }
}
