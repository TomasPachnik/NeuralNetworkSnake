package sk.tomas.neural.util;

import sk.tomas.neural.exception.FileException;
import sk.tomas.neural.model.NeuralNetworkModel;

import java.io.*;

public class Util {

    static double randomInit() {
        return ((Math.random() * 2) - 1);
    }

    static double sigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    public static double sigmoidDerivate(double x) {
        return x * (1 - x);
    }

    public static double[] simplify(double[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 0) {
                array[i] = 1;
            } else {
                array[i] = 0;
            }
        }
        return array;
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

    public static double crossEntropy(double[] truePrediction, double[] probablyPrediction) {
        double result = 0;
        for (int i = 0; i < truePrediction.length; i++) {
            result += (truePrediction[i] * Math.log(probablyPrediction[i]));
        }
        return ((-1) * result);
    }

    static double outputError(double expected, double given) {
        return given - expected;
    }

    static double outputInput(double x) {
        return x * (1 - x);
    }

    static double errorAffect(double expected, double given, double outputBefore) {
        return outputError(expected, given) * outputInput(given) * outputBefore;
    }

    static double errorAffectWithMistake(double mistake, double given, double outputBefore) {
        return mistake * outputInput(given) * outputBefore;
    }

    static double calculateNewWeight(double originalWeight, double learningRate, double errorAffect) {
        return originalWeight - (learningRate * errorAffect);
    }

    private static double squaredError(double target, double error) {
        return (Math.pow((target - error), 2)) / 2;
    }

    static double squaredError(double[] truePrediction, double[] probablyPrediction) {
        double result = 0;
        for (int i = 0; i < truePrediction.length; i++) {
            result += squaredError(truePrediction[i], probablyPrediction[i]);
        }
        return result;
    }

    public static void writeFile(String filename, NeuralNetworkModel network) throws FileException {
        try {
            FileOutputStream f = new FileOutputStream(new File(filename + ".dat"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(network);
            o.close();
            f.close();
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    public static NeuralNetworkModel readFile(String filename) throws FileException {
        try {
            FileInputStream fi = new FileInputStream(new File(filename + ".dat"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            NeuralNetworkModel network = (NeuralNetworkModel) oi.readObject();
            oi.close();
            fi.close();
            return network;
        } catch (IOException | ClassNotFoundException e) {
            throw new FileException(e);
        }
    }

    public static boolean deleteFile(String filename) {
        File file = new File(filename + ".dat");
        return file.delete();
    }

    public static Object clone(Object orig) {
        Object obj = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
