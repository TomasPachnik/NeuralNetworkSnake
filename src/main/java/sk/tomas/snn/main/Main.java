package sk.tomas.snn.main;

import sk.tomas.snake.Control;
import sk.tomas.snake.Core;
import sk.tomas.snake.Direction;
import sk.tomas.snn.core.NeuralNetwork;
import sk.tomas.snn.func.Func;

public class Main {
    /*
        public static void main(String[] args) {
            NeuralNetwork neuralNetwork = new NeuralNetwork();
            neuralNetwork.randomizeWeights();
            int number = 5000;
            for (int i = 0; i < number; i++) {
                neuralNetwork.teach(7, 3, true);
                neuralNetwork.teach(1, 0, false);
            }
            System.out.println(neuralNetwork.calculate(7, 2));
            System.out.println(neuralNetwork.calculate(2, 0));
        }
    */
    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork();
        neuralNetwork.randomizeWeights();
        int number = 100;
        for (int i = 0; i < number; i++) {
            boolean alive = true;
            Control snake = new Core();
            ((Core) snake).setPrintMove(false);

            while (alive) {
                Direction bestMove = snake.calculateBestMove();
                double[] move = neuralNetwork.teach(snake.wallUp(), snake.wallDown(), snake.wallLeft(), snake.wallRight(),
                        snake.appleUp(), snake.appleDown(), snake.appleLeft(), snake.appleRight(),
                        snake.bodyUp(), snake.bodyDown(), snake.bodyLeft(), snake.bodyRight(),
                        Func.expected(bestMove));
                Direction neuralMove = Func.calcDirection(move);
                alive = snake.move(neuralMove);
                System.out.println(move + " - " + Func.expected(bestMove));
                if (!alive) {
                    System.out.println(snake.getScore());
                }
            }
        }
    }


}
