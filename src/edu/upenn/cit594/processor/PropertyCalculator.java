package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.utils.MarketValueComparator;

import java.util.*;

public class PropertyCalculator {
    protected Set<Area> areaSetByMarketValue = new TreeSet<>(new MarketValueComparator());
    protected Map<String, Double> averageLivableAreaByZipcode = new HashMap<>();
    protected Map<String, Double> averageMarketValueByZipcode = new HashMap<>();
    protected Map<String, Double> marketValuePerCapitaByZipcode = new HashMap<>();

    /**
     * Calculates the average market value for a zipcode
     * @param zipcode user entered zipcode
     * @param properties list of all properties
     * @return average market value for a zipcode
     */
    public double calculateAverageMarketValueByZipcode(String zipcode, List<Property> properties) {
        double averageMarketValue = calculateAverageByZipcode(zipcode, new MarketValueMetrics(), properties);
        averageMarketValueByZipcode.put(zipcode, averageMarketValue);

        return averageMarketValue;
    }

    /**
     * Calculates the livable area for a zipcode
     * @param zipcode user entered zipcode
     * @param properties list of all properties
     * @return average livable area for a zipcode
     */
    public double calculateAverageLivableAreaByZipcode(String zipcode, List<Property> properties) {
        double averageLivableArea = calculateAverageByZipcode(zipcode, new LivableAreaMetrics(), properties);
        averageLivableAreaByZipcode.put(zipcode, averageLivableArea);

        return averageLivableArea;
    }

    /**
     * Calculates average by zipcode using the strategy pattern (either livable area or market value)
     * @param zipcode entered by user
     * @param metrics Metric class that determines whether we sum and count the livable area or market value
     * @param properties list of all properties
     * @return the average determined by the passed Metrics class
     */
    protected double calculateAverageByZipcode(String zipcode, Metrics metrics, List<Property> properties) {
        if(zipcode.isEmpty()) {
            return 0.0;
        }
        
        this.computePropertyMetricsByZipcode(zipcode, metrics, properties);
        if (metrics.count == 0) return 0.0;
        double averageByZipcode = metrics.average();

        return averageByZipcode;
    }

    /**
     * calculate residential market value per capita for a given zipcode
     * @param zipcode entered by user
     * @param areas map of zipcode to population
     * @param properties list of all properties
     * @return residential market value per capital for a zipcode
     */
    protected double calculateResidentialMarketValuePerCapita(String zipcode, Map<String, Area> areas, List<Property> properties) {
        if (!areas.containsKey(zipcode)) {
            return 0.0;
        }
    
        double populationOfZipcode = areas.get(zipcode).getPopulation();

        if(Double.compare(0, populationOfZipcode) == 0 || zipcode.isEmpty()) {
            return 0;
        }

        MarketValueMetrics marketValuePropertyCalculator = new MarketValueMetrics();
        computePropertyMetricsByZipcode(zipcode, marketValuePropertyCalculator, properties);

        double marketValueSumOfZipcode = marketValuePropertyCalculator.sum;
        double marketValuePerCapita = marketValueSumOfZipcode / populationOfZipcode;

        marketValuePerCapitaByZipcode.put(zipcode, marketValuePerCapita);

        return marketValuePerCapita;
    }

    /**
     * Sorts by highest market value per capita to lowest and presents the fine count
     * @param properties list of all properties
     * @param areaMap map of zipcodes to population
     * @param zipcodeToParkingViolationMetricsMap map of zip codes to ParkingViolationMetrics
     * @return set of areas that have fine count sorted by market value per capita
     */
    protected Set<Area> calculateFineCountForHighestMarketValuePerCapitaAreas(List<Property> properties, Map<String, Area> areaMap, Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap) {
        for (Map.Entry<String, Area> entry : areaMap.entrySet()) {
            Area area = entry.getValue();

            double marketValuePerCapita = calculateResidentialMarketValuePerCapita(area.getZipcode(), areaMap, properties);

            int fineCount = zipcodeToParkingViolationMetricsMap.get(area.getZipcode()) != null ? zipcodeToParkingViolationMetricsMap.get(area.getZipcode()).count : 0;

            area.setMarketValuePerCapita(marketValuePerCapita);
            area.setFineCount(fineCount);

            if(!Double.isNaN(area.getMarketValuePerCapita())) this.areaSetByMarketValue.add(area);
        }

        return this.areaSetByMarketValue;
    }

    /**
     * Computes the desired metrics for a list of properties
     * @param zipcode user defined zipcode
     * @param metrics Metrics class determines the attribute we sum and count
     * @param properties list of properties
     */
    private void computePropertyMetricsByZipcode(String zipcode, Metrics metrics, List<Property> properties) {
        for (Property property : properties) {
            if (property.getZipcode().equals(zipcode)) {
                metrics.sumAndCountMetric(property);
            }
        }
    }
}
