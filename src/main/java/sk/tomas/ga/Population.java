package sk.tomas.ga;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Individual> population;

    public Population() {
        this.population = new ArrayList<>();
    }

    public List<Individual> getPopulation() {
        return population;
    }
}
