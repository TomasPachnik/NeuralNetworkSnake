package sk.tomas.neural.model;

import java.io.Serializable;
import sk.tomas.neural.FileException;
import sk.tomas.neural.Network;

public interface NeuralNetworkModel extends Serializable {

    double[] run(double[] input);

    void saveState(String filename) throws FileException;

    void loadState(String filename) throws FileException;

    boolean deleteState(String filename);

    Network getClone();

}
