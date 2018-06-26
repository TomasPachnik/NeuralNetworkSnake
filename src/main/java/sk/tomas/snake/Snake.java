package sk.tomas.snake;

public interface Snake {

    boolean move(Direction direction);

    boolean move(double[] array);

    boolean move(double left, double forward, double right);

    int getScore();

    double [] directionToArray(Direction direction);

    Direction calculateBestMove();

    int wallLeft();

    int wallRight();

    int wallUp();

    int wallDown();

    int appleLeft();

    int appleRight();

    int appleUp();

    int appleDown();

    int bodyLeft();

    int bodyRight();

    int bodyUp();

    int bodyDown();

    double[] output();

}
