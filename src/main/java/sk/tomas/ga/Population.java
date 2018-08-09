package sk.tomas.ga;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import sk.tomas.neural.model.NeuralNetworkModel;
import sk.tomas.neural.model.NeuralNetworkModelImpl;

class Population implements Serializable {

    private List<Individual> population;
    private final int networkRuns;

    Population(int networkRuns) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
    }

    Population(int populationSize, int networkRuns, NeuralNetworkModel model) {
        this.population = new ArrayList<>();
        this.networkRuns = networkRuns;
        initPopulation(populationSize, model);
    }

    List<Individual> getPopulation() {
        return population;
    }

    private void initPopulation(int populationSize, NeuralNetworkModel model) {
        int i = 0;
        while (i < populationSize) {
            population.add(new Individual(model.getClone()));
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
