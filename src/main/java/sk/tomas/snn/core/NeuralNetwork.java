package sk.tomas.snn.core;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private List<List<Neural>> network;

    private int[] config = {2, 2, 1};

    public NeuralNetwork() {
        network = new ArrayList<>();
        for (int aConfig : config) {
            List<Neural> item = new ArrayList<>();
            for (int i = 0; i < aConfig; i++) {
                item.add(new Neural());
            }
            network.add(item);
        }
    }

    public int run(int input) {
        for (List<Neural> list : network) {
            System.out.println(list.size());
        }

        return 0;
    }


}
