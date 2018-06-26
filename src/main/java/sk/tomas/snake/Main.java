package sk.tomas.snake;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Snake app = new SnakeImpl();
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println(app.calculateBestMove());
            String input = keyboard.nextLine();
            if (input != null) {
                if ("p".equals(input)) {
                    exit = true;
                } else
                    switch (input) {
                        case "w":
                            exit = !app.move(Direction.UP);
                            break;
                        case "a":
                            exit = !app.move(Direction.LEFT);
                            break;
                        case "s":
                            exit = !app.move(Direction.DOWN);
                            break;
                        case "d":
                            exit = !app.move(Direction.RIGHT);
                            break;
                    }

            }
        }
        keyboard.close();
    }

}
