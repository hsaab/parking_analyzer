package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;

import java.util.Map;

public class AreaCalculator {
    private int populationSum = 0;

    protected int sumPopulations(Map<String, Area> areaMap) {
        if (getPopulationSum() != 0) {
            return getPopulationSum();
        }

        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            this.populationSum = this.getPopulationSum() + (int) entry.getValue().getPopulation();
        }

        return this.getPopulationSum();
    }
    
    protected int getPopulationSum() {
        return populationSum;
    }
}
