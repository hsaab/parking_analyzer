package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.MarketValueComparator;

import java.util.*;

public class ParkingViolationCalculator {
    protected static Map<String,Double> calculateTotalFinesPerCapita(Map<String, Area> areaMap, List<ParkingViolation> violations) {
        Map<String, Double> zipcodeToFineMap = new TreeMap<>();
        Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = buildParkingViolationMetricsByZipcode(areaMap, violations);

        for (Map.Entry<String, ParkingViolationMetrics> entry : zipcodeToParkingViolationMetricsMap.entrySet()) {
            double finesPerCapita = entry.getValue().sum / areaMap.get(entry.getKey()).population;
            zipcodeToFineMap.put(entry.getKey(), finesPerCapita);
        }

        return zipcodeToFineMap;
    }

    public static Set<Area> calculateFineCountForHighestMarketValuePerCapitaAreas(List<Property> properties, Map<String, Area> areaMap, List<ParkingViolation> violations) {
        Set<Area> areaSetByMarketValue = new TreeSet<>(new MarketValueComparator()); // sorts from high to low
        Map<String, Integer> zipcodeToFineCountMap = new HashMap<>();

        Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = buildParkingViolationMetricsByZipcode(areaMap, violations);

        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            Area area = entry.getValue();

            double marketValuePerCapita = PropertyCalculator.calculateResidentialMarketValuePerCapita(area.zipcode, areaMap, properties);
            int fineCount = zipcodeToFineCountMap.get(area.zipcode) != null ? zipcodeToFineCountMap.get(area.zipcode) : 0;

            area.setMarketValuePerCapita(marketValuePerCapita);
            area.setFineCount(fineCount);

            areaSetByMarketValue.add(area);
        }

        return areaSetByMarketValue;
    }

    public static Map<String, ParkingViolationMetrics> buildParkingViolationMetricsByZipcode(Map<String, Area> areaMap, List<ParkingViolation> violations) {
        Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = new TreeMap<>();

        for (ParkingViolation violation : violations) {
            if (areaMap.containsKey((violation.zipcode))) {
                if (zipcodeToParkingViolationMetricsMap.containsKey(violation.zipcode)) {
                    ParkingViolationMetrics parkingViolationMetrics = zipcodeToParkingViolationMetricsMap.get(violation.zipcode);
                    parkingViolationMetrics.sumAndCountMetric(violation);
                } else {
                    zipcodeToParkingViolationMetricsMap.put(violation.zipcode, new ParkingViolationMetrics(violation));
                }
            }
        }

        return zipcodeToParkingViolationMetricsMap;
    }
}
