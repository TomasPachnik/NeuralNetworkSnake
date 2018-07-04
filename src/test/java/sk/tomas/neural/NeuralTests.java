package sk.tomas.neural;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class NeuralTests {

    @Test
    public void neuralTest() {
        int inputNeurons = 2;
        Network network = new NetworkImpl(inputNeurons, 2, 1, 0.5);
        Random random = new Random();
        double randomNumber;
        double[] input;
        for (int i = 0; i < 10000; i++) {
            randomNumber = random.nextInt(inputNeurons);
            input = new double[inputNeurons];
            for (int j = 0; j < inputNeurons; j++) {
                if (randomNumber == 1) {
                    input[j] = 1;
                }
            }
            double[] expected = new double[1];
            if (randomNumber == 1) {
                expected[0] = 1;
            }
            network.teach(input, expected);
        }
        double errorRate = network.errorRate();
        Assert.assertTrue("Expected mistake <0.05, actually it was: " + errorRate, errorRate < 0.05);
    }


    @Test
    public void loadSaveDeleteTest() {
        String filename = "loadSaveTest";
        Network network = new NetworkImpl(4, 2, 1, 0.4);
        try {
            network.saveState(filename);
            network.loadState(filename);
            network.deleteState(filename);
        } catch (FileException e) {
            Assert.fail("Error loading or saving file: " + e.getMessage());
        }
    }

    @Test
    public void testtt() {
        Network network = new NetworkImpl(2, 2, 1, 0.4);
        network.setWeight(1, 0, 0, 3.633942326468051);
        network.setWeight(1, 0, 1, 2.947643361088329);
        network.setWeight(1, 1, 0, -0.42928908915423536);
        network.setWeight(1, 1, 1, 0.795061178619576);
        network.setWeight(2, 0, 1, 11.05761194498972);
        network.setWeight(2, 0, 0, -14.913480086518929);
        double[] input = new double[]{0, 1};
        double[] result = network.run(input);
        System.out.println(result[0]);
    }

}
