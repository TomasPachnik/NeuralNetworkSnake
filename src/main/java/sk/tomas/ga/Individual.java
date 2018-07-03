package sk.tomas.ga;

import sk.tomas.neural.InputException;
import sk.tomas.neural.Network;
import sk.tomas.snake.Movement;
import sk.tomas.snake.Snake;
import sk.tomas.snake.SnakeImpl;

class Individual {

    private Network network;
    private double score = 0;

    Individual(Network network) {
        this.network = network;
    }

    double getFitness() {
        return score;
    }

    void setNetwork(Network network) {
        this.network = network;
    }

    Network getNetwork() {
        return network;
    }

    void run() throws InputException {
        Snake snake = new SnakeImpl(10, 10);
        boolean alive = true;

        while (alive) {
            double[] move = network.run(snake.actualInfo());
            alive = snake.move(Movement.map(move));
            if (snake.helpScore() < -100) {
                alive = false;
            }
        }
        if (snake.helpScore() > score) {
            score = snake.helpScore();
        }
    }

}
