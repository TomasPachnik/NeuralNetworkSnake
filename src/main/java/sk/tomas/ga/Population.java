package sk.tomas.ga;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import sk.tomas.neural.config.NeuralConfiguration;
import sk.tomas.neural.model.NeuralNetworkModel;

class Population implements Serializable {

    private List<Individual> population;
    private final int networkRuns;

    Population(int networkRuns) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
    }

    Population(int populationSize, int networkRuns, NeuralConfiguration configuration) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
        initPopulation(populationSize, configuration);
    }

    List<Individual> getPopulation() {
        return population;
    }

    private void initPopulation(int populationSize, NeuralConfiguration configuration) {
        int i = 0;
        while (i < populationSize) {
            population.add(new Individual(configuration.build()));
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
            if (individual.getFitness() > 0) {
                sum += individual.getFitness();
            }
        }
        return sum;
    }

}
