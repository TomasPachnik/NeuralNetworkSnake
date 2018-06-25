package sk.tomas.snn.main;

import sk.tomas.neural.InputException;
import sk.tomas.neural.Network;
import sk.tomas.neural.NetworkImpl;
import sk.tomas.neural.Util;
import sk.tomas.snake.Control;
import sk.tomas.snake.Core;
import sk.tomas.snake.Direction;
import sk.tomas.snn.func.Func;

public class Main {

    public static void main(String[] args) throws InputException, InterruptedException {
        Network neuralNetwork = new NetworkImpl();
        int max = 10000;
        for (int i = 0; i < max; i++) {
            boolean alive = true;
            Control snake = new Core();
            ((Core) snake).setPrintMove(false);

            while (alive) {
                Direction bestMove = snake.calculateBestMove();

                double input[] = new double[]{snake.wallUp(), snake.wallDown(), snake.wallLeft(), snake.wallRight(),
                        snake.appleUp(), snake.appleDown(), snake.appleLeft(), snake.appleRight(),
                        snake.bodyUp(), snake.bodyDown(), snake.bodyLeft(), snake.bodyRight()};

                double[] move = neuralNetwork.teach(Util.softMax(input), Func.expected(bestMove));

                Direction neuralMove = Func.calcDirection(move);
                alive = snake.move(neuralMove);
            }
            if (i % (max / 100) == 0) {
                System.out.println(i/100);
            }
        }

        Control snake = new Core();
        boolean alive = true;
        while (alive) {
            double input[] = new double[]{snake.wallUp(), snake.wallDown(), snake.wallLeft(), snake.wallRight(),
                    snake.appleUp(), snake.appleDown(), snake.appleLeft(), snake.appleRight(),
                    snake.bodyUp(), snake.bodyDown(), snake.bodyLeft(), snake.bodyRight()};

            double[] move = neuralNetwork.run(Util.softMax(input));
            alive = snake.move(Func.calcDirection(move));
            //alive = snake.move(snake.calculateBestMove());
            System.out.println(snake.getScore());
            Thread.sleep(1000);
        }
    }


}
