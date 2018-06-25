package sk.tomas.neural;

public class Util {

    static double randomInit() {
        return ((Math.random() * 2) - 1);
    }

    public static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    public static double sigmoidDerivate(double x) {
        return x * (1 - x);
    }

    public static double[] softMax(double[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = Math.exp(array[i]);
        }
        double sum = 0;
        for (double v : array) {
            sum += v;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] / sum;
        }
        return array;
    }

    static double crossEntropy(double[] truePrediction, double[] probablyPrediction) {
        double result = 0;
        for (int i = 0; i < truePrediction.length; i++) {
            result += (truePrediction[i] * Math.log(probablyPrediction[i]));
        }
        return ((-1) * result);
    }

    static double outputError(double expected, double given) {
        return given - expected;
    }

    public static double outputInput(double x) {
        return x * (1 - x);
    }

    public static double errorAffect(double expected, double given, double outputBefore) {
        return outputError(expected, given) * outputInput(given) * outputBefore;
    }

    static double errorAffectWithMistake(double mistake, double given, double outputBefore) {
        return mistake * outputInput(given) * outputBefore;
    }

    public static double calculateNewWeight(double originalWeight, double learningRate, double errorAffect) {
        return originalWeight - (learningRate * errorAffect);
    }

    private static double squaredError(double target, double error) {
        return (Math.pow((target - error), 2)) / 2;
    }

    public static double squaredError(double[] truePrediction, double[] probablyPrediction) {
        double result = 0;
        for (int i = 0; i < truePrediction.length; i++) {
            result += squaredError(truePrediction[i], probablyPrediction[i]);
        }
        return result;
    }

}
