package sk.tomas.neural;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;
import sk.tomas.ga.Genetic;
import sk.tomas.ga.GeneticImpl;
import sk.tomas.ga.Strategy;
import sk.tomas.snake.Movement;
import sk.tomas.snake.Snake;
import sk.tomas.snake.SnakeImpl;

public class GeneticAlgorithmTest {

    @Test
    public void normalDistributionTest() {
        NormalDistribution normalDistribution = new NormalDistribution(1000, 100);
        System.out.println(normalDistribution.cumulativeProbability(1300));
    }

    @Test
    public void GATest() throws InterruptedException {
        Strategy strategy = new StrategyImpl();

        Genetic genetic = new GeneticImpl(0.7, 0.05, 50, 30, 100, 6, 4, 3, strategy);
        Network best = genetic.run();

        Snake snake = ((StrategyImpl) strategy).getSnake();
        snake.print();
        boolean alive = true;
        while (alive) {
            double[] move = best.run(snake.actualInfoLite());
            alive = snake.move(Movement.map(move));
            snake.print();
            Thread.sleep(300);
        }
        System.out.println(snake.score());
    }

    @Test
    public void snakePlayTest() throws InterruptedException {
        Network network = new NetworkImpl(6, 4, 3);
        try {
            network.loadState("best");
        } catch (FileException e) {
            System.out.println("Save not found, network is in initial random state");
        }

        Snake snake = new SnakeImpl(10, 10);
        boolean alive = true;
        snake.print();
        while (alive) {
            alive = snake.move(Movement.map(network.run(snake.actualInfoLite())));
            snake.print();
            Thread.sleep(500);
            if (snake.fitness() < -100) {
                alive = false;
            }
        }
        System.out.println("score: " + snake.score());

    }


}
