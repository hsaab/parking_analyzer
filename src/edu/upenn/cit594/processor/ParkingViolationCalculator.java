package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.data.ParkingViolation;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ParkingViolationCalculator {
    protected Map<String, Double> zipcodeToFineMap = new TreeMap<>();
    protected Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = new TreeMap<>();

    protected Map<String,Double> calculateTotalFinesPerCapita(Map<String, Area> areaMap, List<ParkingViolation> violations) {
        Map<String, ParkingViolationMetrics> zipcodeToParkingViolationMetricsMap = buildParkingViolationMetricsByZipcode(areaMap, violations);

        for (Map.Entry<String, ParkingViolationMetrics> entry : zipcodeToParkingViolationMetricsMap.entrySet()) {
            String zipcode = entry.getKey();
            double sumFines = entry.getValue().sum;
            double population = areaMap.get(entry.getKey()).getPopulation();
            double finesPerCapita = sumFines / population;
            if (sumFines == 0 || population == 0 || Double.isNaN(finesPerCapita)) { // ignore zipcodes with NaN or 0 in fines
                continue;
            }
            this.zipcodeToFineMap.put(zipcode, finesPerCapita);
        }

        return this.zipcodeToFineMap;
    }

    protected Map<String, ParkingViolationMetrics> buildParkingViolationMetricsByZipcode(Map<String, Area> areaMap, List<ParkingViolation> violations) {
        for (ParkingViolation violation : violations) {
            if (areaMap.containsKey(violation.zipcode) && violation.getState().equals("PA")) { // if the zipcode of this violation isn't in areaMap, skip it
                if (this.zipcodeToParkingViolationMetricsMap.containsKey(violation.zipcode)) {
                    ParkingViolationMetrics parkingViolationMetrics = this.zipcodeToParkingViolationMetricsMap.get(violation.zipcode);
                    parkingViolationMetrics.sumAndCountMetric(violation);
                } else {
                    this.zipcodeToParkingViolationMetricsMap.put(violation.zipcode, new ParkingViolationMetrics(violation));
                }
            }
        }

        return this.zipcodeToParkingViolationMetricsMap;
    }
}
