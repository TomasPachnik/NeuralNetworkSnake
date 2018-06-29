package sk.tomas.neural;

public interface Network {

    double[] teach(double[] input, double[] expected) throws InputException;

    double[] run(double[] input) throws InputException;

    void saveState(String filename) throws FileException;

    void loadState(String filename) throws FileException;

    boolean deleteState(String filename);

    Network getClone();

    double errorRate();

    double getWeight(int fromLayer, int fromIndex, int ancestorIndex);

    void setWeight(int  fromLayer, int fromIndex, int ancestorIndex, double value);

}
