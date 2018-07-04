package sk.tomas.neural;

import sk.tomas.ga.Strategy;
import sk.tomas.snake.Movement;
import sk.tomas.snake.Snake;
import sk.tomas.snake.SnakeImpl;

public class StrategyImpl implements Strategy {

    @Override
    public double run(Network network) {
        Snake snake = new SnakeImpl(20, 10);
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

    Snake getSnake() {
        return new SnakeImpl(20, 10);
    }

}
