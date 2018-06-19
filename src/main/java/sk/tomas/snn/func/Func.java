package sk.tomas.snn.func;

public class Func {

    /**
     * calculate sigmoid function for x
     *
     * @param x
     * @return
     */
    public static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    /**
     * return number between -1 and 1
     *
     * @return
     */
    public static double randomInit() {
        return ((Math.random() * 2) - 1);
    }

}
