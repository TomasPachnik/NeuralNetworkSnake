package sk.tomas.snake;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Snake app = new SnakeImpl();
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            double move[] = app.output();
            double hint[] = app.directionToArray(app.calculateBestMove());
            for (double aMove : move) {
                System.out.print(aMove + " ");
            }
            System.out.println();
            for (double aHint : hint) {
                System.out.print(aHint + " ");
            }
            System.out.println();
            String input = keyboard.nextLine();
            if (input != null) {
                if ("p".equals(input)) {
                    exit = true;
                } else
                    switch (input) {
                        case "w":
                            exit = !app.move(0, 1, 0);
                            break;
                        case "a":
                            exit = !app.move(1, 0, 0);
                            break;
                        case "d":
                            exit = !app.move(0, 0, 1);
                            break;
                    }
            }
        }
        keyboard.close();
    }

}
