package sk.tomas.ga;

import sk.tomas.neural.NetworkImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Population  implements Serializable {

    private List<Individual> population;
    private final int networkRuns;

    Population(int networkRuns) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
    }

    Population(int populationSize, int networkRuns, int inputLayer, int hiddenLayer, int outputLayer) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
        initPopulation(populationSize, inputLayer, hiddenLayer, outputLayer);
    }

    List<Individual> getPopulation() {
        return population;
    }

    private void initPopulation(int populationSize, int inputLayer, int hiddenLayer, int outputLayer) {
        int i = 0;
        while (i < populationSize) {
            population.add(new Individual(new NetworkImpl(inputLayer, hiddenLayer, outputLayer)));
            i++;
        }
    }

    void execute(Strategy strategy) {
        for (Individual individual : population) {
            for (int i = 0; i < networkRuns; i++) {
                individual.run(strategy);
            }
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
