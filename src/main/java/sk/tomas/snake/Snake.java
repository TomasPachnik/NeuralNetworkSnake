package sk.tomas.snake;

public interface Snake {

    boolean move(Movement movement);

    void print();

    int score();

    int fitness();

    double [] actualInfo();

    double[] actualInfoLite();

}
