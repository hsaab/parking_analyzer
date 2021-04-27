package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;

import java.util.Map;

public class AreaCalculator {
    protected static int sumPopulations(Map<String, Area> areaMap) {
        int populationSum = 0;

        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            populationSum += entry.getValue().population;
        }

        return populationSum;
    }
}
