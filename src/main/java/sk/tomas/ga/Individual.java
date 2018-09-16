package sk.tomas.ga;

import java.io.Serializable;
import sk.tomas.neural.model.NeuralNetworkModel;

class Individual implements Serializable {

    private NeuralNetworkModel network;
    private double fitness = 0;
    private double runs = 0;

    Individual(NeuralNetworkModel network) {
        this.network = network;
    }

    double getFitness() {
        if (runs != 0) {
            return fitness / runs;
        }
        return 0;
    }

    void setNetwork(NeuralNetworkModel network) {
        this.network = network;
    }

    NeuralNetworkModel getNetwork() {
        return network;
    }

    void run(Strategy strategy) {
        fitness += strategy.run(network);
        runs++;
    }

}
