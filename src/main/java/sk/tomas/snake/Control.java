package sk.tomas.snake;

public interface Control {

    boolean move(Direction direction);

    int getScore();

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

}
