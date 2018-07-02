package sk.tomas.ga;

public class Util {

    public static double normalDistributionForMutation() {
        return 0.08 * Math.sqrt(-2 * Math.log(Math.random())) * Math.sin(2 * Math.PI * Math.random());
    }

}
