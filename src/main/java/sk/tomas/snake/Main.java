package sk.tomas.snake;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Control app = new Core();
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            String input = keyboard.nextLine();
            if (input != null) {
                if ("p".equals(input)) {
                    exit = true;
                } else
                    switch (input) {
                        case "w":
                            app.move(Direction.UP);
                            break;
                        case "a":
                            app.move(Direction.LEFT);
                            break;
                        case "s":
                            app.move(Direction.DOWN);
                            break;
                        case "d":
                            app.move(Direction.RIGHT);
                            break;
                    }
            }
        }
        keyboard.close();
    }

}
