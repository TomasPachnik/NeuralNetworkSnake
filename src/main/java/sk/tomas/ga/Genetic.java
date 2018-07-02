package sk.tomas.ga;

import sk.tomas.neural.InputException;
import sk.tomas.neural.Network;

import java.util.Random;

public class Genetic {

    private final double CROSS_RATE = 0.7; //crossing probability 0.7 - 1.0
    private final double MUTATION_RATE = 0.05; //mutation of each gene probability
    private final int POPULATION_SIZE = 50; //number of individuals in population
    private final int HIDDEN_LAYER_DEEP = 1;
    private final int OUTPUT_LAYER_DEEP = 2;

    private Population population;

    public void run() throws InputException {
        Random crossingRandom = new Random();
        Random parentRandom = new Random();
        Random selectionRandom = new Random();
        Random mutationRandom = new Random();


        Double bestScore = 0d;
        Population newPopulation;
        while (bestScore < 50) {
            newPopulation = new Population();

            if (population == null) { //population zero
                population = new Population(POPULATION_SIZE, 4, 2, 1);
            }

            population.execute();//calculate fitness of each individual

            //TODO subtract from all fitnesses value of individual with minimal fitness value -> this individual will never be picked up

            //crossing
            for (int i = 0; i < POPULATION_SIZE / 2; i++) {
                Network parent1 = selection(population, selectionRandom); //selection
                Network parent2 = selection(population, selectionRandom); //selection
                Network[] children = cross(parent1, parent2, crossingRandom, parentRandom);
                newPopulation.getPopulation().add(new Individual(children[0]));
                newPopulation.getPopulation().add(new Individual(children[1]));
            }

            //mutation
            for (Individual individual : population.getPopulation()) {
                mutate(individual.getNetwork(), mutationRandom);
            }

            bestScore = population.getBest().getFitness();
            newPopulation.getPopulation().set(0, population.getBest()); //elitism
            population = newPopulation;
            System.out.println(bestScore);
        }
    }

    //roulette selection algorithm
    private Network selection(Population population, Random selectionRandom) {
        double sum = 0;
        double fitnessPoint = selectionRandom.nextDouble() * population.getSumFitness();
        for (Individual individual : population.getPopulation()) {
            sum += individual.getFitness();
            if (sum > fitnessPoint) {
                return individual.getNetwork();
            }
        }
        throw new RuntimeException("wrong selection -> this should not happen at all");
    }

    private Network[] cross(Network parent1, Network parent2, Random crossingRandom, Random parentRandom) {
        Network child1 = parent1.getClone();
        Network child2 = parent1.getClone();
        Network[] children = new Network[]{child1, child2};

        if (crossingRandom.nextDouble() < CROSS_RATE) {
            children = cross(parent1, parent2, children, parent1.getHiddenLayer(), parent1.getInputLayer(), HIDDEN_LAYER_DEEP, parentRandom);
            children = cross(parent1, parent2, children, parent1.getOutputLayer(), parent1.getHiddenLayer(), OUTPUT_LAYER_DEEP, parentRandom);
        }

        return children;
    }

    private Network mutate(Network network, Random mutationRandom) {

        network = mutate(network, network.getHiddenLayer(), network.getInputLayer(), HIDDEN_LAYER_DEEP, mutationRandom);
        network = mutate(network, network.getOutputLayer(), network.getHiddenLayer(), OUTPUT_LAYER_DEEP, mutationRandom);

        return network;
    }

    private Network[] cross(Network parent1, Network parent2, Network[] children, int bottomLayer, int upperLayer, int bottomLayerDeep, Random parentRandom) {
        for (int i = 0; i < bottomLayer; i++) {
            for (int j = 0; j < upperLayer; j++) {
                if (parentRandom.nextBoolean()) { //get from parent1
                    children[0].setWeight(bottomLayerDeep, i, j, parent1.getWeight(bottomLayerDeep, i, j));
                    children[1].setWeight(bottomLayerDeep, i, j, parent2.getWeight(bottomLayerDeep, i, j));
                } else { //get from parent2
                    children[0].setWeight(bottomLayerDeep, i, j, parent2.getWeight(bottomLayerDeep, i, j));
                    children[1].setWeight(bottomLayerDeep, i, j, parent1.getWeight(bottomLayerDeep, i, j));
                }
            }
        }
        return children;
    }

    private Network mutate(Network network, int bottomLayer, int upperLayer, int bottomLayerDeep, Random mutationRandom) {
        double weight;
        for (int i = 0; i < bottomLayer; i++) {
            for (int j = 0; j < upperLayer; j++) {
                if (mutationRandom.nextDouble() < MUTATION_RATE) {
                    weight = network.getWeight(bottomLayerDeep, i, j);
                    weight += Util.normalDistributionForMutation();
                    if (weight >= -1 && weight <= 1) {
                        network.setWeight(bottomLayerDeep, i, j, weight);
                    }
                }
            }
        }
        return network;
    }

}
