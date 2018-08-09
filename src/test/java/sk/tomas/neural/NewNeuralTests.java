package sk.tomas.neural;

import org.junit.Test;
import sk.tomas.neural.config.NeuralConfiguration;
import sk.tomas.neural.config.enums.Activation;
import sk.tomas.neural.config.enums.WeightInit;
import sk.tomas.neural.model.NeuralNetworkModel;

public class NewNeuralTests {

    @Test
    public void testNeuralConfig() {

        NeuralNetworkModel model = new NeuralConfiguration()
                .initSeed(1L)
                .initWeight(WeightInit.RANDOM)
                .addLayer(0, 6, false, Activation.NONE)
                .addLayer(1, 4, true, Activation.SIGMOID)
                .addLayer(2, 3, true, Activation.SIGMOID)
                .backpropagation(false)
                .build();
        System.out.println();
    }


}
