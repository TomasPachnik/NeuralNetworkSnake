package sk.tomas.ga;

import sk.tomas.neural.FileException;

import java.io.Serializable;
import java.util.Random;
import sk.tomas.neural.config.NeuralConfiguration;
import sk.tomas.neural.model.NeuralNetworkModel;

public class GeneticImpl implements Genetic, Serializable {

    private double crossRate = 0.7; //crossing probability 0.7 - 1.0
    private double mutationRate = 0.05; //mutation of each gene probability 0.05
    private int populationSize = 50; //number of individuals in population 50
    private int generations = 30; //number of generations
    private int networkRuns = 1; //number of iteration for every network
    private String saveAfterEachGeneration = null;
    private boolean save; //save actual state
    private NeuralConfiguration configuration; //instance of neural network
    private Strategy strategy; // interface for strategy patterns, which implement network run
    //randoms
    private Random crossingRandom;
    private Random parentRandom;
    private Random selectionRandom;
    private Random mutationRandom;
    private Random gaussianRandom;

    private Population population;
    private final String geneticSave = "geneticSave";

    public GeneticImpl(NeuralConfiguration configuration, Strategy strategy) {
        this.configuration = configuration;
        this.strategy = strategy;
    }

    public GeneticImpl(double crossRate, double mutationRate, int populationSize, int generations, int networkRuns,
            NeuralConfiguration configuration, Strategy strategy) {
        this.crossRate = crossRate;
        this.mutationRate = mutationRate;
        this.populationSize = populationSize;
        this.generations = generations;
        this.networkRuns = networkRuns;
        this.configuration = configuration;
        this.strategy = strategy;
    }

    @Override
    public NeuralNetworkModel run() throws FileException {
        crossingRandom = new Random();
        parentRandom = new Random();
        selectionRandom = new Random();
        mutationRandom = new Random();
        gaussianRandom = new Random();

        int index = 0;
        Population newPopulation;
        while (index < generations) {

            newPopulation = new Population(networkRuns);

            if (population == null) { //population zero
                population = new Population(populationSize, networkRuns, configuration);
                population.execute(strategy);//calculate fitness of each individual
                System.out.println("population zero performed");
            }

            //crossing
            for (int i = 0; i < population.getPopulation().size() / 2; i++) {
                Individual parent1 = selection(population); //selection
                Individual parent2 = selection(population); //selection
                NeuralNetworkModel[] children = cross(parent1.getNetwork(), parent2.getNetwork());
                newPopulation.getPopulation().add(new Individual(children[0]));
                newPopulation.getPopulation().add(new Individual(children[1]));
            }

            //mutation
            for (Individual individual : newPopulation.getPopulation()) {
                individual.setNetwork(mutate(individual.getNetwork()));
            }

            newPopulation.execute(strategy);//calculate fitness of each individual

            newPopulation.getPopulation().set(0, population.getBest()); //elitism
            population = newPopulation;
            index++;
            System.out.println("index: " + index + " fitness: " + population.getBest().getFitness());

            if (saveAfterEachGeneration != null) {
                save(population.getBest().getNetwork(), saveAfterEachGeneration);
            }
            if (save) {
                Util.writeFile(geneticSave, this);
            }

        }

        return population.getBest().getNetwork();
    }

    //roulette selection algorithm
    private Individual selection(Population population) {
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

    private NeuralNetworkModel[] cross(NeuralNetworkModel parent1, NeuralNetworkModel parent2) {
        NeuralNetworkModel child1 = parent1.getClone();
        NeuralNetworkModel child2 = parent1.getClone();
        NeuralNetworkModel[] children = new NeuralNetworkModel[]{child1, child2};

        if (crossingRandom.nextDouble() < crossRate) {
            for (int i = 1; i < parent1.getNetwork().size(); i++) {
                children = cross(parent1, parent2, children, parent1.getNetwork().get(i).size(),
                        parent1.getNetwork().get(i - 1).size(), i);
            }
        }
        return children;
    }

    private NeuralNetworkModel[] cross(NeuralNetworkModel parent1, NeuralNetworkModel parent2,
            NeuralNetworkModel[] children, int bottomLayer, int upperLayer, int bottomLayerDeep) {
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

    private NeuralNetworkModel mutate(NeuralNetworkModel network) {
        for (int i = 1; i < network.getNetwork().size(); i++) {
            network = mutate(network, network.getNetwork().get(i).size(), network.getNetwork().get(i - 1).size(), i);
        }
        return network;
    }

    private NeuralNetworkModel mutate(NeuralNetworkModel network, int bottomLayer, int upperLayer,
            int bottomLayerDeep) {
        double weight;
        for (int i = 0; i < bottomLayer; i++) {
            for (int j = 0; j < upperLayer; j++) {
                if (mutationRandom.nextDouble() < mutationRate) {
                    weight = network.getWeight(bottomLayerDeep, i, j);
                    weight += gaussianRandom.nextGaussian();
                    network.setWeight(bottomLayerDeep, i, j, weight);
                }
            }
        }
        return network;
    }

    private void save(NeuralNetworkModel network, String name) {
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

    @Override
    public void autoSave(boolean save) {
        this.save = save;
    }

    private Population getPopulation() {
        return population;
    }

    private double getCrossRate() {
        return crossRate;
    }

    private double getMutationRate() {
        return mutationRate;
    }

    private int getPopulationSize() {
        return populationSize;
    }

    private int getGenerations() {
        return generations;
    }

    private int getNetworkRuns() {
        return networkRuns;
    }

    private String getSaveAfterEachGeneration() {
        return saveAfterEachGeneration;
    }

    private boolean isSave() {
        return save;
    }

    private Strategy getStrategy() {
        return strategy;
    }

    private NeuralConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void loadState() throws FileException {
        Genetic genetic = Util.readFile(geneticSave);
        population = ((GeneticImpl) genetic).getPopulation();
        crossRate = ((GeneticImpl) genetic).getCrossRate();
        mutationRate = ((GeneticImpl) genetic).getMutationRate();
        mutationRate = ((GeneticImpl) genetic).getMutationRate();
        populationSize = ((GeneticImpl) genetic).getPopulationSize();
        generations = ((GeneticImpl) genetic).getGenerations();
        networkRuns = ((GeneticImpl) genetic).getNetworkRuns();
        saveAfterEachGeneration = ((GeneticImpl) genetic).getSaveAfterEachGeneration();
        save = ((GeneticImpl) genetic).isSave();
        configuration = ((GeneticImpl) genetic).getConfiguration();
        strategy = ((GeneticImpl) genetic).getStrategy();
    }

}
