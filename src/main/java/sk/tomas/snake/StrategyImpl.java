package sk.tomas.snake;

import sk.tomas.ga.Strategy;
import sk.tomas.neural.model.NeuralNetworkModel;

public class StrategyImpl implements Strategy {

    private final int width;
    private final int height;

    StrategyImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double run(NeuralNetworkModel network) {
        Snake snake = new SnakeImpl(width, height);
        boolean alive = true;
        double[] move;
        while (alive) {
            move = network.run(snake.actualInfoLite());
            alive = snake.move(Movement.map(move));
            if (snake.fitness() < -100) {
                alive = false;
            }
        }
        return snake.fitness();
    }

}
