package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.MarketValueComparator;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PropertyCalculator {
    public static double calculateAverageByZipcode(String zipcode, Metrics metrics, List<Property> properties) {
        computePropertyMetricsByZipcode(zipcode, metrics, properties);

        return metrics.average();
    }

    public static double calculateResidentialMarketValuePerCapita(String zipcode, Map<String, Area> areas, List<Property> properties) {
        double populationOfZipcode = areas.get(zipcode).population;

        if(Double.compare(0, populationOfZipcode) == 0) {
            return 0;
        }

        MarketValueMetrics marketValuePropertyCalculator = new MarketValueMetrics();

        computePropertyMetricsByZipcode(zipcode, marketValuePropertyCalculator, properties);

        double marketValueSumOfZipcode = marketValuePropertyCalculator.sum;

        return marketValueSumOfZipcode / populationOfZipcode;
    }

    public static Set<Area> calculateFineCountForHighestMarketValuePerCapitaAreas(List<Property> properties, Map<String, Area> areaMap, Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap) {
        Set<Area> areaSetByMarketValue = new TreeSet<>(new MarketValueComparator()); // sorts from high to low

        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            Area area = entry.getValue();

            double marketValuePerCapita = calculateResidentialMarketValuePerCapita(area.zipcode, areaMap, properties);

            int fineCount = zipcodeToParkingViolationMetricsMap.get(area.zipcode) != null ? zipcodeToParkingViolationMetricsMap.get(area.zipcode).count : 0;

            area.setMarketValuePerCapita(marketValuePerCapita);
            area.setFineCount(fineCount);

            if(!Double.isNaN(area.marketValuePerCapita)) areaSetByMarketValue.add(area);
        }

        return areaSetByMarketValue;
    }

    protected static void computePropertyMetricsByZipcode(String zipcode, Metrics metrics, List<Property> properties) {
        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                metrics.sumAndCountMetric(property);
            }
        }
    }
}
