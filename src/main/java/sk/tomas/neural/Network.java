package sk.tomas.neural;

public interface Network {

    double[] teach(double[] input, double[] expected);

    double[] run(double[] input);

    void saveState(String filename) throws FileException;

    void loadState(String filename) throws FileException;

    boolean deleteState(String filename);

    Network getClone();

    double errorRate();

    double getWeight(int fromLayer, int fromIndex, int ancestorIndex);

    void setWeight(int fromLayer, int fromIndex, int ancestorIndex, double value);

    double getLearningRate();

    int getInputLayer();

    int getHiddenLayer();

    int getOutputLayer();

}
