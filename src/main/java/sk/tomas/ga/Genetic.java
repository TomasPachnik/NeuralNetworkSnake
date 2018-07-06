package sk.tomas.ga;

import sk.tomas.neural.FileException;
import sk.tomas.neural.Network;

public interface Genetic {

    Network run() throws FileException;

    void setCrossRate(double crossRate);

    void setMutationRate(double mutationRate);

    void setPopulationSize(int populationSize);

    void setGenerations(int generations);

    void setNetworkRuns(int networkRuns);

    void saveAfterEachGeneration(boolean save, String name);

    void autoSave(boolean save);

    void loadState() throws FileException;

}
