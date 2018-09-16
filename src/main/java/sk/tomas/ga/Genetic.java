package sk.tomas.ga;

import sk.tomas.neural.exception.FileException;
import sk.tomas.neural.model.NeuralNetworkModel;

public interface Genetic {

    NeuralNetworkModel run() throws FileException;

    void setCrossRate(double crossRate);

    void setMutationRate(double mutationRate);

    void setPopulationSize(int populationSize);

    void setGenerations(int generations);

    void setNetworkRuns(int networkRuns);

    void saveAfterEachGeneration(boolean save, String name);

    void autoSave(boolean save);

    void loadState() throws FileException;

}
