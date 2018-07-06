package sk.tomas.ga;

import sk.tomas.neural.FileException;
import sk.tomas.neural.Network;

import java.util.Random;

public class GeneticImpl implements Genetic {

    private double crossRate = 0.7; //crossing probability 0.7 - 1.0
    private double mutationRate = 0.05; //mutation of each gene probability 0.05
    private int populationSize = 50; //number of individuals in population 50
    private int generations = 30; //number of generations
    private int networkRuns = 1; //number of iteration for every network
    private String saveAfterEachGeneration = null;
    private final int inputLayerSize; //number of neurons of input layer
    private final int hiddenLayerSize; //number of neurons of middle hidden layer
    private final int outputLayerSize; //number of neurons of output layer
    private Strategy strategy; // interface for strategy patterns, which implement network run
    private final int HIDDEN_LAYER_DEEP = 1;
    private final int OUTPUT_LAYER_DEEP = 2;

    private Population population;

    public GeneticImpl(int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Strategy strategy) {
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        this.strategy = strategy;
    }

    public GeneticImpl(double crossRate, double mutationRate, int populationSize, int generations, int networkRuns, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Strategy strategy) {
        this.crossRate = crossRate;
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.generations = generations;
        this.networkRuns = networkRuns;
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        this.strategy = strategy;
    }

    @Override
    public Network run() {
        Random crossingRandom = new Random();
        Random parentRandom = new Random();
        Random selectionRandom = new Random();
        Random mutationRandom = new Random();

        int index = 0;
        Population newPopulation;
        while (index < generations) {

            newPopulation = new Population(networkRuns);

            if (population == null) { //population zero
                population = new Population(populationSize, networkRuns, inputLayerSize, hiddenLayerSize, outputLayerSize);
                population.execute(strategy);//calculate fitness of each individual
            }

            //crossing
            for (int i = 0; i < population.getPopulation().size() / 2; i++) {
                Individual parent1 = selection(population, selectionRandom); //selection
                Individual parent2 = selection(population, selectionRandom); //selection
                Network[] children = cross(parent1.getNetwork(), parent2.getNetwork(), crossingRandom, parentRandom);
                newPopulation.getPopulation().add(new Individual(children[0]));
                newPopulation.getPopulation().add(new Individual(children[1]));
            }

            //mutation
            for (Individual individual : newPopulation.getPopulation()) {
                individual.setNetwork(mutate(individual.getNetwork(), mutationRandom));
            }

            newPopulation.execute(strategy);//calculate fitness of each individual

            newPopulation.getPopulation().set(0, population.getBest()); //elitism
            population = newPopulation;
            index++;
            System.out.println("index: " + index + " fitness: " + population.getBest().getFitness());

            if (saveAfterEachGeneration != null) {
                save(population.getBest().getNetwork(), saveAfterEachGeneration);
            }
        }

        return population.getBest().getNetwork();
    }

    //roulette selection algorithm
    private Individual selection(Population population, Random selectionRandom) {
        double sum = 0;
        double dd = selectionRandom.nextDouble();
        double fitnessPoint = dd * population.getSumFitness();
        for (Individual individual : population.getPopulation()) {
            if (individual.getFitness() > 0) {
                sum += individual.getFitness();
                if (sum > fitnessPoint) {
                    return individual;
                }
            }
        }
        throw new RuntimeException("wrong selection -> this should not happen at all");
    }

    private Network[] cross(Network parent1, Network parent2, Random crossingRandom, Random parentRandom) {
        Network child1 = parent1.getClone();
        Network child2 = parent1.getClone();
        Network[] children = new Network[]{child1, child2};

        if (crossingRandom.nextDouble() < crossRate) {
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
                if (mutationRandom.nextDouble() < mutationRate) {
                    weight = network.getWeight(bottomLayerDeep, i, j);
                    weight += Util.normalDistributionForMutation();
                    network.setWeight(bottomLayerDeep, i, j, weight);
                }
            }
        }
        return network;
    }

    private void save(Network network, String name) {
        try {
            network.saveState(name);
        } catch (FileException e) {
            System.out.println("Could not save network state.");
        }
    }

    @Override
    public void setCrossRate(double crossRate) {
        this.crossRate = crossRate;
    }

    @Override
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    @Override
    public void setGenerations(int generations) {
        this.generations = generations;
    }

    @Override
    public void setNetworkRuns(int networkRuns) {
        this.networkRuns = networkRuns;
    }

    @Override
    public void saveAfterEachGeneration(boolean save, String name) {
        if (save && name != null) {
            this.saveAfterEachGeneration = name;
        } else {
            this.saveAfterEachGeneration = null;
        }
    }

}
