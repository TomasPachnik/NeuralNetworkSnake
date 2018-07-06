package sk.tomas.ga;

import sk.tomas.neural.Network;

import java.io.Serializable;

public interface Strategy extends Serializable {

    /**
     *
     * @param network neural network, on which test will be performed
     * @return fitness value of actual neural network
     */
    double run(Network network);

}
