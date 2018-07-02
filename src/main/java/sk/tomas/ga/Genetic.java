package sk.tomas.ga;

import sk.tomas.neural.Network;
import sk.tomas.neural.NetworkImpl;

import java.util.Random;

public class Genetic {

    private final double CROSS_RATE = 0.7; //crossing probability 0.7 - 1.0
    private final double MUTATION_RATE = 0.05; //mutation of each gene probability
    private final int POPULATION_SIZE = 80; //number of individuals in population
    private final int HIDDEN_LAYER_DEEP = 1;
    private final int OUTPUT_LAYER_DEEP = 2;

    private Population population;

    private void fitness() {

    }

    private Network selection(Population population, double sumFitness, Random selectionRandom) {
        double sum = 0;
        double fitnessPoint = selectionRandom.nextDouble() * sumFitness;
        for (Individual individual : population.getPopulation()) {
            sum += individual.getFitness();
            if (sum > fitnessPoint) {
                return individual.getNetwork();
            }
        }
        return null;
    }

    private Network cross(Network parent1, Network parent2, Random crossingRandom, Random parentRandom) {
        Network child = new NetworkImpl(parent1.getInputLayer(), parent1.getHiddenLayer(), parent1.getOutputLayer(), parent1.getLearningRate());

        child = cross(parent1, parent2, child, parent1.getHiddenLayer(), parent1.getInputLayer(), HIDDEN_LAYER_DEEP, crossingRandom, parentRandom);
        child = cross(parent1, parent2, child, parent1.getOutputLayer(), parent1.getHiddenLayer(), OUTPUT_LAYER_DEEP, crossingRandom, parentRandom);

        return child;
    }

    private Network mutate(Network network, Random mutationRandom) {

        network = mutate(network, network.getHiddenLayer(), network.getInputLayer(), HIDDEN_LAYER_DEEP, mutationRandom);
        network = mutate(network, network.getOutputLayer(), network.getHiddenLayer(), OUTPUT_LAYER_DEEP, mutationRandom);

        return network;
    }

    private Network cross(Network parent1, Network parent2, Network child, int bottomLayer, int upperLayer, int bottomLayerDeep, Random crossingRandom, Random parentRandom) {
        for (int i = 0; i < bottomLayer; i++) {
            for (int j = 0; j < upperLayer; j++) {
                if (crossingRandom.nextDouble() < CROSS_RATE) {
                    if (parentRandom.nextBoolean()) { //get from parent1
                        child.setWeight(bottomLayerDeep, i, j, parent1.getWeight(bottomLayerDeep, i, j));
                    } else { //get from parent2
                        child.setWeight(bottomLayerDeep, i, j, parent2.getWeight(bottomLayerDeep, i, j));
                    }
                }
            }
        }
        return child;
    }

    private Network mutate(Network network, int bottomLayer, int upperLayer, int bottomLayerDeep, Random mutationRandom) {
        for (int i = 0; i < bottomLayer; i++) {
            for (int j = 0; j < upperLayer; j++) {
                if (mutationRandom.nextDouble() < MUTATION_RATE) {
                    double weight = network.getWeight(bottomLayerDeep, i, j);
                    weight += Util.normalDistributionForMutation();
                    network.setWeight(bottomLayerDeep, i, j, weight);
                }
            }
        }
        return network;
    }

}
