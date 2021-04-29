package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ParkingViolation;

public class ParkingViolationMetrics extends Metrics<ParkingViolation> {
    /**
     * Constructor
     */
    public ParkingViolationMetrics() {
        super();
    }

    /**
     * Constructor
     * @param violation the first value to sum and count
     */
    public ParkingViolationMetrics(ParkingViolation violation) {
        super(violation);
    }

    /**
     * Keeps a count and sum of parking violation fines
     * @param parkingViolation
     */
    @Override
    public void sumAndCountMetric(ParkingViolation parkingViolation) {
        if (Double.isNaN(parkingViolation.getFine())) return;
        this.count++;
        this.sum += parkingViolation.getFine();
    }
}
