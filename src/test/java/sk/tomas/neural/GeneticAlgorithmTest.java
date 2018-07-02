package sk.tomas.neural;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;
import sk.tomas.ga.Genetic;

public class GeneticAlgorithmTest {

    @Test
    public void normalDistributionTest() {
        NormalDistribution normalDistribution = new NormalDistribution(1000, 100);
        System.out.println(normalDistribution.cumulativeProbability(1300));
    }

    @Test
    public void GATest() throws InputException {
        Genetic genetic = new Genetic();
        genetic.run();
    }

}
