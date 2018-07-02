package sk.tomas.ga;

import sk.tomas.neural.Network;

class Individual {

    private Network network;
    private double fitness;

    public double getFitness() {
        return fitness;
    }

    public Network getNetwork() {
        return network;
    }
}
