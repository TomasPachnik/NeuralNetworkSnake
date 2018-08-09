package sk.tomas.neural;

import java.util.Random;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;

public class GeneticAlgorithmTest {

    @Test
    public void normalDistributionTest() {
        NormalDistribution normalDistribution = new NormalDistribution(1000, 100);
        System.out.println(normalDistribution.cumulativeProbability(1300));
    }

    @Test
    public void gaussianTest(){
        Random random = new Random();
        for(int i=0;i<100;i++){
            System.out.println(random.nextGaussian());
        }

    }

}
