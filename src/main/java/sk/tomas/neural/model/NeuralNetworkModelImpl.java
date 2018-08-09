package sk.tomas.neural.model;

import java.util.List;
import sk.tomas.neural.FileException;
import sk.tomas.neural.Network;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NeuralNetworkModelImpl implements NeuralNetworkModel {

    private List<List<Neural>> network;

    public void setNetwork(List<List<Neural>> network) {
        this.network = network;
    }

    @Override
    public double[] run(double[] input) {
        throw new NotImplementedException();
    }

    @Override
    public void saveState(String filename) throws FileException {
        throw new NotImplementedException();
    }

    @Override
    public void loadState(String filename) throws FileException {
        throw new NotImplementedException();
    }

    @Override
    public boolean deleteState(String filename) {
        throw new NotImplementedException();
    }

    @Override
    public Network getClone() {
        throw new NotImplementedException();
    }
}
