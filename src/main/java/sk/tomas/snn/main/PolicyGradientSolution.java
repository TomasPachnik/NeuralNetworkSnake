package sk.tomas.snn.main;

import sk.tomas.neural.FileException;
import sk.tomas.neural.InputException;
import sk.tomas.neural.Network;
import sk.tomas.neural.NetworkImpl;
import sk.tomas.snake.Snake;
import sk.tomas.snake.SnakeImpl;

import java.util.Random;

public class PolicyGradientSolution {

    public static void main(String[] args) throws InputException, FileException, InterruptedException {
        teach();
    }

    private static void teach() throws InputException, InterruptedException, FileException {
        Network network = new NetworkImpl(6, 4, 3, 0.5);
        Network clone;
        try {
            network.loadState("pgs");
        } catch (FileException e) {
            System.out.println("Game not found.");
        }
        int count = 0;
        for (int i = 0; i < 100000; i++) {
            clone = network.getClone();
            Snake snake = new SnakeImpl();
             ((SnakeImpl) snake).setPrintMove(false);
            boolean alive = true;
            while (alive) {
                double[] move;
                Random random1 = new Random();
                Random random2 = new Random();
                Random random3 = new Random();
                double[] expected = new double[]{random1.nextDouble(), random2.nextDouble(), random3.nextDouble()};
                move = clone.teach(snake.output(), expected);
                alive = snake.move(move);
                // System.out.println("game: " + i + " score: " + snake.getScore());
                //Thread.sleep(300);
            }
            if (snake.getScore() > 20) {
                count++;
                //System.out.println("good game - best score: " + snake.getScore());
                network = clone;
            }
        }
        System.out.println("count: " + count);
        network.saveState("pgs");
    }


}
