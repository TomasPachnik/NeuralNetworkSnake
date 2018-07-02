package sk.tomas.ga;

class Util {

    //TODO check
    static double normalDistributionForMutation() {
        return 0.08 * Math.sqrt(-2 * Math.log(Math.random())) * Math.sin(2 * Math.PI * Math.random());
    }

}
