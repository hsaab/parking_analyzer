package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.MarketValueComparator;

import java.util.*;

public class PropertyCalculator {
    Set<Area> areaSetByMarketValue = new TreeSet<>(new MarketValueComparator());
    Map<String, Double> averageLivableAreaByZipcode = new HashMap<>();
    Map<String, Double> averageMarketValueByZipcode = new HashMap<>();
    Map<String, Double> marketValuePerCapitaByZipcode = new HashMap<>();

    protected double calculateAverageByZipcode(String zipcode, Metrics metrics, List<Property> properties) {
        this.computePropertyMetricsByZipcode(zipcode, metrics, properties);

        double averageByZipcode = metrics.average();

        this.putAverageToMapByZipcode(zipcode, metrics, averageByZipcode);

        return averageByZipcode;
    }

    protected double calculateResidentialMarketValuePerCapita(String zipcode, Map<String, Area> areas, List<Property> properties) {
        double populationOfZipcode = areas.get(zipcode).population;

        if(Double.compare(0, populationOfZipcode) == 0) {
            return 0;
        }

        MarketValueMetrics marketValuePropertyCalculator = new MarketValueMetrics();
        computePropertyMetricsByZipcode(zipcode, marketValuePropertyCalculator, properties);

        double marketValueSumOfZipcode = marketValuePropertyCalculator.sum;
        double marketValuePerCapita = marketValueSumOfZipcode / populationOfZipcode;

        marketValuePerCapitaByZipcode.put(zipcode, marketValuePerCapita);

        return marketValuePerCapita;
    }

    protected Set<Area> calculateFineCountForHighestMarketValuePerCapitaAreas(List<Property> properties, Map<String, Area> areaMap, Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap) {
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            Area area = entry.getValue();

            double marketValuePerCapita = calculateResidentialMarketValuePerCapita(area.zipcode, areaMap, properties);

            int fineCount = zipcodeToParkingViolationMetricsMap.get(area.zipcode) != null ? zipcodeToParkingViolationMetricsMap.get(area.zipcode).count : 0;

            area.setMarketValuePerCapita(marketValuePerCapita);
            area.setFineCount(fineCount);

            if(!Double.isNaN(area.marketValuePerCapita)) this.areaSetByMarketValue.add(area);
        }

        return this.areaSetByMarketValue;
    }

    private void computePropertyMetricsByZipcode(String zipcode, Metrics metrics, List<Property> properties) {
        for (Property property : properties) {
            if (property.zipcode.equals(zipcode)) {
                metrics.sumAndCountMetric(property);
            }
        }
    }

    private void putAverageToMapByZipcode(String zipcode, Metrics metrics, double averageByZipcode) {
        if(metrics.getClass().getSimpleName().equals(LivableAreaMetrics.className)) {
            averageLivableAreaByZipcode.put(zipcode, averageByZipcode);
        }

        if(metrics.getClass().getSimpleName().equals(MarketValueMetrics.className)) {
            averageMarketValueByZipcode.put(zipcode, averageByZipcode);
        }
    }
}
