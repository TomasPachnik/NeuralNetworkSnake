package sk.tomas.neural.model;

import java.util.List;
import sk.tomas.neural.FileException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class NeuralNetworkModelImpl implements NeuralNetworkModel {

    private List<List<Neural>> network;

    public NeuralNetworkModelImpl(List<List<Neural>> network) {
        this.network = network;
    }

    @Override
    public double[] run(double[] input) {
        //set up input to neural input layer
        for (int i = 0; i < network.get(0).size(); i++) {
            network.get(0).get(i).setLastValue(input[i]);
        }
        return run();
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
    public NeuralNetworkModel getClone() {
        throw new NotImplementedException();
    }

    @Override
    public double getWeight(int fromLayer, int fromIndex, int ancestorIndex) {
        return network.get(fromLayer).get(fromIndex).getInputs().get(ancestorIndex).getW();
    }

    @Override
    public void setWeight(int fromLayer, int fromIndex, int ancestorIndex, double value) {
        network.get(fromLayer).get(fromIndex).getInputs().get(ancestorIndex).setW(value);
    }

    @Override
    public List<List<Neural>> getNetwork() {
        return network;
    }

    private double[] run() {
        for (int i = 1; i < network.size(); i++) {
            for (Neural neural : network.get(i)) {
                for (NeuralInput neuralInput : neural.getInputs()) {
                    neuralInput.setX(neuralInput.getAncestor().getLastValue());
                }
                neural.forwardPropagation();
            }
        }
        double[] result = new double[network.get(network.size() - 1).size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = network.get(network.size() - 1).get(i).getLastValue();
        }
        return result;
    }
}
