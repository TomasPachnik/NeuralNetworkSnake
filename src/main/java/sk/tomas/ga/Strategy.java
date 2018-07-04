package sk.tomas.ga;

import sk.tomas.neural.Network;

public interface Strategy {

    /**
     *
     * @param network neural network, on which test will be performed
     * @return fitness value of actual neural network
     */
    double run(Network network);

}
