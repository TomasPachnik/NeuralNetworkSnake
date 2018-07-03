package sk.tomas.ga;

import sk.tomas.neural.InputException;
import sk.tomas.neural.NetworkImpl;

import java.util.ArrayList;
import java.util.List;

class Population {

    private int NETWORK_RUNS = 100;

    private List<Individual> population;

    public Population() {
        this.population = new ArrayList<>();
    }

    public Population(int populationSize, int inputLayer, int hiddenLayer, int outputLayer) {
        this.population = new ArrayList<>();
        initPopulation(populationSize, inputLayer, hiddenLayer, outputLayer);
    }

    public List<Individual> getPopulation() {
        return population;
    }

    private void initPopulation(int populationSize, int inputLayer, int hiddenLayer, int outputLayer) {
        int i = 0;
        while (i < populationSize) {
            population.add(new Individual(new NetworkImpl(inputLayer, hiddenLayer, outputLayer)));
            i++;
        }
    }

    void execute() throws InputException {
        int number = 0;
        for (Individual individual : population) {
            for (int i = 0; i < NETWORK_RUNS; i++) {
                individual.run();
            }
            number++;
         //   System.out.println("individual " + number);
        }

    }

    Individual getBest() {
        double bestScore = -999;
        Individual bestNetwork = null;
        for (Individual individual : population) {
            if (individual.getFitness() > bestScore) {
                bestScore = individual.getFitness();
                bestNetwork = individual;
            }
        }
        return bestNetwork;
    }

    double getSumFitness() {
        double sum = 0;
        for (Individual individual : population) {
            sum += individual.getFitness();
        }
        return sum;
    }

}
