package sk.tomas.ga;

import java.io.Serializable;
import sk.tomas.neural.model.NeuralNetworkModel;

public interface Strategy extends Serializable {

    /**
     *
     * @param network neural network, on which test will be performed
     * @return fitness value of actual neural network
     */
    double run(NeuralNetworkModel network);

}
