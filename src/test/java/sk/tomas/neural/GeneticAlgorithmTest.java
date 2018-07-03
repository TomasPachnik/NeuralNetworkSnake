package sk.tomas.neural;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;
import sk.tomas.ga.Genetic;
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
    public void GATest() throws InputException, InterruptedException, FileException {
        Genetic genetic = new Genetic();
        genetic.run();
    }

    @Test
    public void snakePlayTest() throws FileException, InputException, InterruptedException {
        Network network = new NetworkImpl(6, 4, 3);
        network.loadState("best");

        Snake snake = new SnakeImpl(20, 10);
        boolean alive = true;
        snake.print();
        while (alive) {
            alive = snake.move(Movement.map(network.run(snake.actualInfoLite())));
            snake.print();
            Thread.sleep(200);
        }
        System.out.println("score: " + snake.score());

    }


}
