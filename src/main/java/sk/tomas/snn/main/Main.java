package sk.tomas.snn.main;

import sk.tomas.neural.FileException;
import sk.tomas.neural.InputException;
import sk.tomas.neural.Network;
import sk.tomas.neural.NetworkImpl;
import sk.tomas.snake.Direction;
import sk.tomas.snake.Snake;
import sk.tomas.snake.SnakeImpl;

public class Main {

    public static void main(String[] args) throws InputException, FileException, InterruptedException {
        teach();
    }

    private static void teach() throws FileException, InputException, InterruptedException {
        Network network = new NetworkImpl(9, 12, 3, 0.25);
        String filename = "snake";
        //network.loadState(filename);
        int max = 100;
        for (int i = 0; i < max; i++) {
            System.out.println(i);
            boolean alive = true;
            Snake snake = new SnakeImpl();
            //((SnakeImpl) snake).setPrintMove(false);

            while (alive) {
                Direction bestMove = snake.calculateBestMove();
               // System.out.println(bestMove);
                double[] move = network.teach(snake.output(), snake.directionToArray(bestMove));
                alive = snake.move(move);
                Thread.sleep(400);
            }
            if (i % (max / 100) == 0) {
                network.saveState(filename);
            }
        }
    }

    private static void run() throws InputException, FileException, InterruptedException {
        Network network = new NetworkImpl(9, 6, 3, 0.25);
        String filename = "snake";
       // network.loadState(filename);
        int max = 1000;
        for (int i = 0; i < max; i++) {
            boolean alive = true;
            Snake snake = new SnakeImpl();
            while (alive) {
                double[] move = network.run(snake.output());
                alive = snake.move(move);
                Thread.sleep(400);
            }
        }
    }

}
