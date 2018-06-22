package sk.tomas.snn.core;

import sk.tomas.snn.func.Func;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork implements Serializable {

    private List<List<Neural>> network;

    private double learningRate = 0.25;
    private int[] config = {12, 8, 4};

    public NeuralNetwork() {
        init();
    }

    public boolean calculate(int first, double second) {
        return Func.round(run(first, second));
    }

    private double run(int first, double second) {
        network.get(0).get(0).setLastResult(first);
        network.get(0).get(1).setLastResult(second);

        for (int i = 1; i < network.size(); i++) {
            for (Neural neural : network.get(i)) {
                for (NeuralInput neuralInput : neural.getNeuralInputs()) {
                    neuralInput.setX(neuralInput.getNeural().getLastResult());
                }
                neural.forwardPropagation();
            }
        }
        return network.get(network.size() - 1).get(0).getLastResult();
    }

    private double[] run(double wallUp, double wallDown, double wallLeft, double wallRight,
                         double appleUp, double appleDown, double appleLeft, double appleRight,
                         double bodyUp, double bodyDown, double bodyLeft, double bodyRight) {

        network.get(0).get(0).setLastResult(wallUp);
        network.get(0).get(1).setLastResult(wallDown);
        network.get(0).get(2).setLastResult(wallLeft);
        network.get(0).get(3).setLastResult(wallRight);

        network.get(0).get(4).setLastResult(appleUp);
        network.get(0).get(5).setLastResult(appleDown);
        network.get(0).get(6).setLastResult(appleLeft);
        network.get(0).get(7).setLastResult(appleRight);

        network.get(0).get(8).setLastResult(bodyUp);
        network.get(0).get(9).setLastResult(bodyDown);
        network.get(0).get(10).setLastResult(bodyLeft);
        network.get(0).get(11).setLastResult(bodyRight);

        for (int i = 1; i < network.size(); i++) {
            for (Neural neural : network.get(i)) {
                for (NeuralInput neuralInput : neural.getNeuralInputs()) {
                    neuralInput.setX(neuralInput.getNeural().getLastResult());
                }
                neural.forwardPropagation();
            }
        }

        double[] result = new double[4];
        result[0] = network.get(network.size() - 1).get(0).getLastResult();
        result[1] = network.get(network.size() - 1).get(1).getLastResult();
        result[2] = network.get(network.size() - 1).get(2).getLastResult();
        result[3] = network.get(network.size() - 1).get(3).getLastResult();
        return result;
    }

    public double teach(int first, double second, boolean expected) {
        double netLastResult = run(first, second);
        backPropagation(Func.convert(expected) - netLastResult);
        return netLastResult;
    }

    public double[] teach(double wallUp, double wallDown, double wallLeft, double wallRight,
                          double appleUp, double appleDown, double appleLeft, double appleRight,
                          double bodyUp, double bodyDown, double bodyLeft, double bodyRight, double[] expected
    ) {
        double[] calculation = run(wallUp, wallDown, wallLeft, wallRight, appleUp, appleDown, appleLeft, appleRight, bodyUp, bodyDown, bodyLeft, bodyRight);
        double[] mistake = Func.mistake(calculation, expected);
        for (double v : mistake) {
      //      System.out.print(v + " ");
        }
   //     System.out.println();
        backPropagation(mistake);
        return calculation;
    }

    public void randomizeWeights() {
        for (List<Neural> innerList : network) {
            for (Neural neural : innerList) {
                neural.randomizeWeights();
            }
        }
    }

    private void init() {
        network = new ArrayList<>();
        for (int i = 0; i < config.length; i++) {
            List<Neural> item = new ArrayList<>();
            for (int j = 0; j < config[i]; j++) {
                if (i < 1) {
                    List<NeuralInput> neuralInputs = new ArrayList<>();
                    neuralInputs.add(new NeuralInput());
                    item.add(new Neural(neuralInputs));
                } else {
                    List<NeuralInput> neuralInputs = new ArrayList<>();
                    for (int k = 0; k < config[i - 1]; k++) {
                        neuralInputs.add(new NeuralInput(network.get(i - 1).get(k)));
                    }
                    item.add(new Neural(neuralInputs));
                }
            }
            network.add(item);
        }
    }

    private void backPropagation(double mistake) {
        for (int i = network.size() - 1; i >= 1; i--) {
            for (Neural neural : network.get(i)) {
                neural.backPropagation(learningRate, mistake);
            }
        }
    }

    private void backPropagation(double[] mistake) {
        for (int i = 0; i < mistake.length; i++) {
            network.get(network.size() - 1).get(i).backPropagation(learningRate, mistake[i]);
            for (int j = network.size() - 2; j >= 1; j--) {
                for (Neural neural : network.get(j)) {
                    neural.backPropagation(learningRate, mistake[i]);
                }
            }
        }
    }
}
