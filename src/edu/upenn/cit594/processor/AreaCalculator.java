package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;

import java.util.Map;

public class AreaCalculator {
    protected int populationSum = 0;

    protected int sumPopulations(Map<String, Area> areaMap) {
        if(populationSum != 0) {
            return populationSum;
        }

        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            this.populationSum += entry.getValue().population;
        }

        return this.populationSum;
    }
}
