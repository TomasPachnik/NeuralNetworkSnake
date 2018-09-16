package sk.tomas.snake;

import sk.tomas.ga.Genetic;
import sk.tomas.ga.GeneticImpl;
import sk.tomas.ga.Strategy;
import sk.tomas.neural.exception.FileException;
import sk.tomas.neural.config.NeuralConfiguration;
import sk.tomas.neural.config.enums.Activation;
import sk.tomas.neural.config.enums.WeightInit;
import sk.tomas.neural.model.NeuralNetworkModel;

public class NnMain {

    public static void main(String[] args) throws InterruptedException, FileException {
        Strategy strategy = new StrategyImpl(10, 10);

        Genetic genetic = new GeneticImpl(0.7, 0.05, 50, 100, 100, createConfiguration(), strategy);
        genetic.saveAfterEachGeneration(false, null);
        NeuralNetworkModel best = genetic.run();

        boolean alive = true;
        Snake snake = new SnakeImpl(20, 10);
        snake.print();
        while (alive) {
            alive = snake.move(Movement.map(best.run(snake.actualInfoLite())));
            snake.print();
            Thread.sleep(100);
        }
        System.out.println("score: " + snake.score());
    }

    private static NeuralConfiguration createConfiguration() {
        return new NeuralConfiguration()
                .initWeight(WeightInit.RANDOM)
                .addLayer(0, 6, false, Activation.NONE)
                .addLayer(1, 3, true, Activation.RELU);
    }

}
