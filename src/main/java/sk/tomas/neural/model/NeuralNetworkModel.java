package sk.tomas.neural.model;

import java.io.Serializable;
import java.util.List;
import sk.tomas.neural.exception.FileException;

public interface NeuralNetworkModel extends Serializable {

    double[] run(double[] input);

    void saveState(String filename) throws FileException;

    void loadState(String filename) throws FileException;

    boolean deleteState(String filename);

    NeuralNetworkModel getClone();

    double getWeight(int fromLayer, int fromIndex, int ancestorIndex);

    void setWeight(int fromLayer, int fromIndex, int ancestorIndex, double value);

    List<List<Neural>> getNetwork();

}
