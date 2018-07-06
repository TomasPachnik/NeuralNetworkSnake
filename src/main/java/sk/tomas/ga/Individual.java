package sk.tomas.ga;

import sk.tomas.neural.Network;

import java.io.Serializable;

class Individual implements Serializable {

    private Network network;
    private double fitness = 0;
    private double runs = 0;

    Individual(Network network) {
        this.network = network;
    }

    double getFitness() {
        if (runs != 0) {
            return fitness / runs;
        }
        return 0;
    }

    void setNetwork(Network network) {
        this.network = network;
    }

    Network getNetwork() {
        return network;
    }

    void run(Strategy strategy) {
        fitness += strategy.run(network);
        runs++;
    }

}
