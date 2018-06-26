package sk.tomas.snn.main;

import sk.tomas.neural.*;
import sk.tomas.snake.Control;
import sk.tomas.snake.Core;
import sk.tomas.snake.Direction;
import sk.tomas.snn.func.Func;

public class Main {

    public static void main(String[] args) throws InputException, FileException, InterruptedException {
        run();
    }

    static void teach() throws FileException, InputException {
        Network network = new NetworkImpl();
        String filename = "snake";
        network.loadState(filename);
        int max = 100000;
        for (int i = 0; i < max; i++) {
            System.out.println(i);
            boolean alive = true;
            Control snake = new Core();
            ((Core) snake).setPrintMove(false);

            while (alive) {
                Direction bestMove = snake.calculateBestMove();

                double input[] = new double[]{snake.wallUp(), snake.wallDown(), snake.wallLeft(), snake.wallRight(),
                        snake.appleUp(), snake.appleDown(), snake.appleLeft(), snake.appleRight(),
                        snake.bodyUp(), snake.bodyDown(), snake.bodyLeft(), snake.bodyRight()};

                double[] move = network.teach(Util.simplify(input), Func.expected(bestMove));

                Direction neuralMove = Func.calcDirection(move);
                alive = snake.move(neuralMove);
            }
            if (i % (max / 1000) == 0) {
                network.saveState(filename);
            }
        }
    }

    static void run() throws InputException, FileException, InterruptedException {
        Network network = new NetworkImpl();
        String filename = "snake";
        network.loadState(filename);
        int max = 1000;
        for (int i = 0; i < max; i++) {
            boolean alive = true;
            Control snake = new Core();

            while (alive) {
                double input[] = new double[]{snake.wallUp(), snake.wallDown(), snake.wallLeft(), snake.wallRight(),
                        snake.appleUp(), snake.appleDown(), snake.appleLeft(), snake.appleRight(),
                        snake.bodyUp(), snake.bodyDown(), snake.bodyLeft(), snake.bodyRight()};

                double[] move = network.run(Util.simplify(input));
                alive = snake.move(Func.calcDirection(move));
                Thread.sleep(200);
            }
        }
    }

}
