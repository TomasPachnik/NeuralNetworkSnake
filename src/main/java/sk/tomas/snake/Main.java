package sk.tomas.snake;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Snake app = new SnakeImpl(10, 10);
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            double[] array = app.actualInfo();
            for (int i = 0; i < 120; i++) {
                if (array[i] == 1) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
            app.print();
            String input = keyboard.nextLine();
            if (input != null) {
                if ("p".equals(input)) {
                    exit = true;
                } else
                    switch (input) {
                        case "w":
                            exit = !app.move(Movement.FORWARD);
                            break;
                        case "a":
                            exit = !app.move(Movement.LEFT);
                            break;
                        case "d":
                            exit = !app.move(Movement.RIGHT);
                            break;
                    }

            }
        }
        keyboard.close();
    }

}
